package pl.smarthouse.smartmodule.utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.smarthouse.smartmodule.exceptions.ActorResponseException;
import pl.smarthouse.smartmodule.model.actors.actor.Actor;
import pl.smarthouse.smartmodule.model.configuration.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

import static pl.smarthouse.smartmodule.model.module.ModuleResponses.RESPONSE_MAP;

@AllArgsConstructor
@Slf4j
public class ResponseUtils {
  private static final String SET_RESPONSE = "Actor: {}, Response: {}";

  public static Mono<Map> saveResponses(final Configuration configuration, final Map map) {
    final Map responseMap = (Map) map.get(RESPONSE_MAP);
    return Flux.fromIterable(responseMap.keySet())
        .doOnNext(actorName -> log.info(SET_RESPONSE, actorName, responseMap.get(actorName)))
        .flatMap(actorName -> ActorUtils.getActor(configuration, actorName.toString()))
        .flatMap(actor -> setActorResponse((Actor) actor, responseMap))
        .collectList()
        .thenReturn(map);
  }

  private static Mono<Actor> setActorResponse(final Actor actor, final Map responseMap) {
    try {
      actor.setResponse((Map) responseMap.get(actor.getName()));
      return Mono.just(actor);

    } catch (final Exception e) {
      return Mono.error(new ActorResponseException(e.fillInStackTrace().toString()));
    }
  }
}
