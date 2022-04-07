package pl.smarthouse.smartmodule.model.actors;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class Actor {
  @NonNull protected ActorType type;
  @NonNull protected String name;

  public abstract void setCommand(Command command);
}
