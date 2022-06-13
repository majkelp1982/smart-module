package pl.smarthouse.smartmodule.model.actors;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Getter
@Setter
public class ActorMap {
  Map<String, Actor> actorMap = new HashMap<>();

  public Actor putActor(final Actor actor) {
    return actorMap.put(actor.getName(), actor);
  }

  public Actor getActor(final String name) {
    return actorMap.get(name);
  }

  public Stream<Actor> stream() {
    return actorMap.values().stream();
  }
}
