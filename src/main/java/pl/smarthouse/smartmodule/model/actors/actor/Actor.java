package pl.smarthouse.smartmodule.model.actors.actor;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Map;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.command.CommandSet;
import pl.smarthouse.smartmodule.model.actors.response.Response;
import pl.smarthouse.smartmodule.model.enums.ActorType;

@RequiredArgsConstructor
@Getter
@ToString
public abstract class Actor {
  @NonNull protected ActorType type;
  @NonNull protected String name;

  public abstract CommandSet getCommandSet();

  public abstract Response getResponse();

  public abstract void setResponse(final Map response) throws JsonProcessingException;
}
