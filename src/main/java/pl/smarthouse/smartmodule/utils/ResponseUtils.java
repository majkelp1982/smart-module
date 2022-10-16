package pl.smarthouse.smartmodule.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.smarthouse.smartmodule.model.actors.Actor;
import pl.smarthouse.smartmodule.model.configuration.Configuration;
import reactor.core.publisher.Mono;

import java.util.Map;

import static pl.smarthouse.smartmodule.model.module.ModuleResponses.RESPONSE_MAP;

@AllArgsConstructor
@Slf4j
public class ResponseUtils {
  private static final String SET_RESPONSE = "Actor: {}, Response: {}";

  public static Mono<Map> saveResponses(final Configuration configuration, final Map map) {
    final Map moduleResponses = (Map) map.get(RESPONSE_MAP);
    moduleResponses.forEach(
        (key, response) -> {
          log.info(SET_RESPONSE, key, response);
          final Actor actor = configuration.getActorMap().getActor((String) key);
          try {
            actor.setResponse((Map) response);
          } catch (final JsonProcessingException e) {
            e.printStackTrace();
          }
        });
    return Mono.just(map);
  }
}
