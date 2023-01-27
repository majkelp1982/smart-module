package pl.smarthouse.smartmodule.utils;

import lombok.experimental.UtilityClass;
import pl.smarthouse.smartmodule.model.actors.actor.Actor;
import pl.smarthouse.smartmodule.model.configuration.Configuration;
import reactor.core.publisher.Mono;

@UtilityClass
public class ActorUtils {
  public static Mono<Actor> getActor(final Configuration configuration, final String actorName) {
    return Mono.just(configuration.getActorMap().getActor(actorName));
  }
}
