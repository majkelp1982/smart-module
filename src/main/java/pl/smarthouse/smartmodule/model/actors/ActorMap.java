package pl.smarthouse.smartmodule.model.actors;

import java.util.HashMap;
import java.util.Map;

public class ActorMap {
  Map<String, Actor> actorMap = new HashMap<>();

  public Actor putActor(final Actor actor) {
    return actorMap.put(actor.getName(), actor);
  }

  public Actor getActor(final String name) {
    return actorMap.get(name);
  }
}
