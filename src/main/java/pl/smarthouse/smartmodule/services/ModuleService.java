package pl.smarthouse.smartmodule.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import pl.smarthouse.smartmodule.exceptions.InvalidResponseException;
import pl.smarthouse.smartmodule.model.configuration.Configuration;
import pl.smarthouse.smartmodule.model.module.ModuleCommands;
import pl.smarthouse.smartmodule.utils.CommandUtils;
import pl.smarthouse.smartmodule.utils.ResponseUtils;
import reactor.core.publisher.Mono;

import java.util.Map;

import static pl.smarthouse.smartmodule.model.module.ModuleResponses.TYPE;
import static pl.smarthouse.smartmodule.model.module.ModuleResponses.VERSION;

@RequiredArgsConstructor
@Slf4j
public class ModuleService {
  private static final String WRONG_TYPE = "Type don't match. Should be %s, is: %s";
  private static final String WRONG_VERSION = "Version don't match. Should be %s, is: %s";
  private static final String ERROR_WHILE_EXCHANGE = "Error while exchange with module: {}";
  private static final String CONFIGURATION_MISSING =
      "Configuration is missing in module. Will be send.";
  private static final String ERROR_WHILE_SENDING_CONFIGURATION =
      "Error while sending configuration: {}";
  private static final String DEVICE_RESPOND_ERROR = "Device respond error: {}";

  private final ManagerService managerService;
  private Configuration configuration;
  private final WebClient webClient;

  public void exchange() {
    if (configuration.getBaseUrl() == null) {
      managerService.retrieveModuleIP();
    }
    final ModuleCommands moduleCommands = CommandUtils.getCommandBody(configuration);
    if (moduleCommands.getCommandMap().isEmpty()) {
      log.info("Sending command skipped. No commands to send");
      return;
    }
    log.info("Sending command: {}", moduleCommands);
    exchangeCommand(moduleCommands);
  }

  private void actionOnError(final HttpStatus httpStatus, final String message) {
    if (HttpStatus.PRECONDITION_REQUIRED.equals(httpStatus)) {
      log.info(CONFIGURATION_MISSING, message);
      sendConfigurationToModule();
    }
    if (httpStatus.is4xxClientError()) {
      log.error(DEVICE_RESPOND_ERROR, message);
    }
  }

  public void sendConfigurationToModule() {
    webClient
        .post()
        .uri(configuration.getBaseUrl() + "/configuration")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(configuration)
        .retrieve()
        .bodyToMono(String.class)
        .doOnError(
            throwable -> log.error(ERROR_WHILE_SENDING_CONFIGURATION, throwable.getMessage()))
        .subscribe();
  }

  private void exchangeCommand(final ModuleCommands moduleCommands) {
    webClient
        .post()
        .uri(configuration.getBaseUrl() + "/action")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(moduleCommands)
        .exchangeToMono(this::validateResponse)
        .map(map -> ResponseUtils.saveResponses(configuration, map))
        .doOnError(throwable -> log.error(ERROR_WHILE_EXCHANGE, throwable.getMessage()))
        .subscribe();
  }

  private Mono<Map> validateResponse(final ClientResponse clientResponse) {
    if (clientResponse.statusCode().is2xxSuccessful()) {
      return clientResponse
          .bodyToMono(Map.class)
          .map(this::checkTypeAndVersion)
          .doOnError(throwable -> log.error(ERROR_WHILE_EXCHANGE, throwable.getMessage()));
    } else {
      return clientResponse
          .bodyToMono(Map.class)
          .doOnError(throwable -> log.error(ERROR_WHILE_EXCHANGE, throwable.getMessage()))
          .flatMap(
              map -> {
                actionOnError(clientResponse.statusCode(), (String) map.get("message"));
                return Mono.empty();
              });
    }
  }

  public Map checkTypeAndVersion(final Map map) {
    final String type = map.get(TYPE).toString();
    final String version = map.get(VERSION).toString();
    if (!type.equals(configuration.getType())) {
      throw new InvalidResponseException(String.format(WRONG_TYPE, configuration.getType(), type));
    }
    if (!version.equals(configuration.getVersion())) {
      throw new InvalidResponseException(
          String.format(WRONG_VERSION, configuration.getVersion(), version));
    }
    return map;
  }

  public void setConfiguration(final Configuration configuration) {
    this.configuration = configuration;
  }
}
