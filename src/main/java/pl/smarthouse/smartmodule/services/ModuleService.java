package pl.smarthouse.smartmodule.services;

import static pl.smarthouse.smartmodule.model.module.ModuleResponses.TYPE;
import static pl.smarthouse.smartmodule.model.module.ModuleResponses.VERSION;

import java.util.Map;
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

@RequiredArgsConstructor
@Slf4j
public class ModuleService {
  private static final String WRONG_TYPE = "Type don't match. Should be %s, is: %s";
  private static final String WRONG_VERSION = "Version don't match. Should be %s, is: %s";
  private static final String ERROR_WHILE_EXCHANGE = "Error while exchange, cause: {}, message: {}";
  private static final String ERROR_WHILE_VALIDATION =
      "Error on validation, cause: {}, message: {}";
  private static final String ERROR_WEB_CLIENT = "Error while communicate with module, reason: {}";
  private static final String ERROR_RESET_BASE_URL =
      "Reset module base url caused by connection error";
  private static final String CONFIGURATION_MISSING =
      "Configuration is missing in module. Will be send. Message:{}";
  private static final String ERROR_WHILE_SENDING_CONFIGURATION =
      "Error while sending configuration: {}";
  private static final String DEVICE_RESPOND_ERROR = "Device respond error: {}";

  private final ManagerService managerService;
  private Configuration configuration;
  private final WebClient webClient;

  public Mono<Map> exchange() {
    return Mono.justOrEmpty(configuration.getBaseUrl())
        .switchIfEmpty(Mono.defer(managerService::retrieveModuleIpAndCheckFirmwareVersion))
        .flatMap(ignore -> Mono.just(CommandUtils.getCommandBody(configuration)))
        .flatMap(this::checkModuleCommands)
        .flatMap(this::exchangeCommand)
        .doOnError(
            signal ->
                (signal != null)
                    && (signal.getMessage().contains("connection")
                        || (signal.getMessage().contains("Connection"))),
            exception -> {
              log.error(ERROR_RESET_BASE_URL);
              configuration.resetBaseUrl();
            })
        .onErrorResume(
            throwable -> {
              log.error(ERROR_WHILE_EXCHANGE, throwable.getCause(), throwable.getMessage());
              return Mono.empty();
            });
  }

  private Mono<ModuleCommands> checkModuleCommands(final ModuleCommands moduleCommands) {
    if (moduleCommands.getCommandMap().isEmpty()) {
      log.debug("Sending command skipped. No commands to send");
      return Mono.empty();
    }
    log.info("Sending command: {}", moduleCommands);
    return Mono.just(moduleCommands);
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
        .exchangeToMono(this::processResponse)
        .onErrorResume(
            throwable -> {
              log.error(ERROR_WHILE_SENDING_CONFIGURATION, throwable.getMessage());
              return Mono.empty();
            })
        .subscribe();
  }

  private Mono<Map> exchangeCommand(final ModuleCommands moduleCommands) {
    return webClient
        .post()
        .uri(configuration.getBaseUrl() + "/action")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(moduleCommands)
        .exchangeToMono(this::processResponse)
        .map(this::checkTypeAndVersion)
        .flatMap(map -> ResponseUtils.saveResponses(configuration, map))
        .doOnError(throwable -> log.error(ERROR_WEB_CLIENT, throwable.getMessage()));
  }

  private Mono<Map> processResponse(final ClientResponse clientResponse) {
    if (clientResponse.statusCode().is2xxSuccessful()) {
      return clientResponse
          .bodyToMono(Map.class)
          .doOnError(
              throwable ->
                  log.error(ERROR_WHILE_VALIDATION, throwable.getCause(), throwable.getMessage()));
    } else {
      return clientResponse
          .bodyToMono(Map.class)
          .doOnError(
              throwable ->
                  log.error(ERROR_WHILE_VALIDATION, throwable.getCause(), throwable.getMessage()))
          .flatMap(
              map -> {
                actionOnError(clientResponse.statusCode(), (String) map.get("message"));
                return Mono.empty();
              });
    }
  }

  public Map checkTypeAndVersion(final Map map) {
    if (!configuration.getType().equals(map.get(TYPE))) {
      throw new InvalidResponseException(
          String.format(WRONG_TYPE, configuration.getType(), map.get(TYPE)));
    }
    if (!configuration.getVersion().equals(map.get(VERSION))) {
      throw new InvalidResponseException(
          String.format(WRONG_VERSION, configuration.getVersion(), map.get(VERSION)));
    }
    return map;
  }

  public void setConfiguration(final Configuration configuration) {
    this.configuration = configuration;
  }
}
