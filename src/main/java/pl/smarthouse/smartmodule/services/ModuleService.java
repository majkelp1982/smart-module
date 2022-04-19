package pl.smarthouse.smartmodule.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import pl.smarthouse.smartmodule.model.configuration.Configuration;
import pl.smarthouse.smartmodule.model.module.ModuleCommands;
import pl.smarthouse.smartmodule.model.module.ModuleResponses;
import pl.smarthouse.smartmodule.utils.CommandUtils;
import pl.smarthouse.smartmodule.utils.ResponseUtils;

@RequiredArgsConstructor
@Slf4j
public class ModuleService {

  private Configuration configuration;
  private final WebClient webClient;

  public void sendCommand() {
    final ModuleCommands moduleCommands = CommandUtils.getCommandBody(configuration);
    log.info("Sending command: {}", moduleCommands);
    webClient
        .post()
        .uri(configuration.getBaseUrl() + "/action")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(moduleCommands)
        .retrieve()
        .bodyToMono(ModuleResponses.class)
        .map(moduleResponses -> ResponseUtils.saveResponses(configuration, moduleResponses))
        .subscribe();
  }

  public void setConfiguration(final Configuration configuration) {
    this.configuration = configuration;
  }
}
