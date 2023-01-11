package pl.smarthouse.smartmodule.model.actors.type.Sds011;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import pl.smarthouse.smartmodule.model.actors.actor.Actor;
import pl.smarthouse.smartmodule.model.actors.command.CommandSet;
import pl.smarthouse.smartmodule.model.enums.ActorType;

import java.time.LocalDateTime;
import java.util.Map;

@Setter
@Getter
public class Sds011 extends Actor {
  private Sds011CommandSet commandSet;
  private Sds011Response response;

  public Sds011(@NonNull final String name) {
    super(ActorType.SDS011, name);
    setCommandSet(new Sds011CommandSet(Sds011CommandType.NO_ACTION));
  }

  @Override
  public CommandSet getCommandSet() {
    return commandSet;
  }

  @Override
  public void setResponse(final Map response) {
    final ObjectMapper objectMapper = new ObjectMapper();
    this.response = objectMapper.convertValue(response, Sds011Response.class);
    this.response.setResponseUpdate(LocalDateTime.now());
  }
}