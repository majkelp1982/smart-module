package pl.smarthouse.smartmodule.model.actors;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.smarthouse.smartmodule.model.types.ActorType;

@RequiredArgsConstructor
@Getter
public abstract class Actor {
  @NonNull protected ActorType type;
  @NonNull protected String name;

  public abstract Command getCommand();

  public abstract void setResponse(final String response) throws JsonProcessingException;
}
