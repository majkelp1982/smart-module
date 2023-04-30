package pl.smarthouse.smartmodule.model.actors.actor;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.Setter;

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
