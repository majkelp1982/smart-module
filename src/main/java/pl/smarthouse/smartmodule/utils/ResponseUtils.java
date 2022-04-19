package pl.smarthouse.smartmodule.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import pl.smarthouse.smartmodule.model.configuration.Configuration;
import pl.smarthouse.smartmodule.model.module.ModuleResponses;

@AllArgsConstructor
public class ResponseUtils {

  public static ModuleResponses saveResponses(
      final Configuration configuration, final ModuleResponses moduleResponses) {
    moduleResponses
        .getResponseMap()
        .forEach(
            (key, response) -> {
              try {
                configuration.getActorMap().getActor(key).setResponse(response);
              } catch (final JsonProcessingException e) {
                e.printStackTrace();
              }
            });
    return moduleResponses;
  }
}
