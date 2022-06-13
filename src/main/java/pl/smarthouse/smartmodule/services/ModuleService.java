package pl.smarthouse.smartmodule.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import pl.smarthouse.smartmodule.model.configuration.Configuration;
import pl.smarthouse.smartmodule.model.module.ModuleCommands;
import pl.smarthouse.smartmodule.model.module.ModuleResponses;
import pl.smarthouse.smartmodule.utils.CommandUtils;
import pl.smarthouse.smartmodule.utils.ResponseUtils;

@RequiredArgsConstructor
@Slf4j
public class ModuleService {

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

  private void actionOnError(final HttpStatus httpStatus) {
    if (HttpStatus.PRECONDITION_REQUIRED.equals(httpStatus)) {
      sendConfigurationToModule();
    }
    if (httpStatus.is4xxClientError()) {
      System.out.println("400");
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
        .doOnError(throwable -> log.error(throwable.getMessage()))
        .block();
  }

  private void exchangeCommand(final ModuleCommands moduleCommands) {
    webClient
        .post()
        .uri(configuration.getBaseUrl() + "/action")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(moduleCommands)
        .retrieve()
        .bodyToMono(ModuleResponses.class)
        .map(moduleResponses -> ResponseUtils.saveResponses(configuration, moduleResponses))
        .doOnError(
            WebClientResponseException.class, throwable -> actionOnError(throwable.getStatusCode()))
        .doOnError(throwable -> log.error(throwable.getMessage()))
        .subscribe();
  }

  public void setConfiguration(final Configuration configuration) {
    this.configuration = configuration;
  }
}
