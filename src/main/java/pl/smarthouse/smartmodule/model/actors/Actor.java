package pl.smarthouse.smartmodule.model.actors;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.smarthouse.smartmodule.model.types.ActorType;

@RequiredArgsConstructor
@Getter
public abstract class Actor {
  @NonNull protected ActorType type;
  @NonNull protected String name;

  public abstract void setCommand(Command command);
}
