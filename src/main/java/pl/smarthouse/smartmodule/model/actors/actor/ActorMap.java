package pl.smarthouse.smartmodule.model.actors.actor;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Getter
@Setter
public class ActorMap {
  Map<String, Actor> actors = new HashMap<>();

  public Actor putActor(final Actor actor) {
    return actors.put(actor.getName(), actor);
  }

  public Actor getActor(final String name) {
    return actors.get(name);
  }

  public Stream<Actor> stream() {
    return actors.values().stream();
  }
}
