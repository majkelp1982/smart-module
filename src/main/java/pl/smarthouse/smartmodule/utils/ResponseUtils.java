package pl.smarthouse.smartmodule.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.smarthouse.smartmodule.model.configuration.Configuration;
import pl.smarthouse.smartmodule.model.module.ModuleResponses;

@AllArgsConstructor
@Slf4j
public class ResponseUtils {
  private static final String SET_RESPONSE = "Actor: {}, Response: {}";

  public static ModuleResponses saveResponses(
      final Configuration configuration, final ModuleResponses moduleResponses) {
    moduleResponses
        .getResponseMap()
        .forEach(
            (key, response) -> {
              try {
                log.info(SET_RESPONSE, key, response);
                configuration.getActorMap().getActor(key).setResponse(response);
              } catch (final JsonProcessingException e) {
                e.printStackTrace();
              }
            });
    return moduleResponses;
  }
}
