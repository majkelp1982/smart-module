package pl.smarthouse.smartmodule.model.actors.actor;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.command.CommandSet;
import pl.smarthouse.smartmodule.model.types.ActorType;

import java.util.Map;

@RequiredArgsConstructor
@Getter
@ToString
public abstract class Actor {
  @NonNull protected ActorType type;
  @NonNull protected String name;

  public abstract CommandSet getCommandSet();

  public abstract void setResponse(final Map response) throws JsonProcessingException;
}
