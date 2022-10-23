package pl.smarthouse.smartmodule.model.actors.typelibs.SDS011;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import pl.smarthouse.smartmodule.model.actors.actor.Actor;
import pl.smarthouse.smartmodule.model.actors.command.CommandSet;
import pl.smarthouse.smartmodule.model.types.ActorType;

import java.time.LocalDateTime;
import java.util.Map;

@Setter
@Getter
public class SDS011 extends Actor {
  private SDS011CommandSet commandSet;
  private SDS011Response response;

  public SDS011(@NonNull final String name) {
    super(ActorType.SDS011, name);
    setCommandSet(new SDS011CommandSet(SDS011CommandType.NO_ACTION));
  }

  @Override
  public CommandSet getCommandSet() {
    return commandSet;
  }

  @Override
  public void setResponse(final Map response) throws JsonProcessingException {
    final ObjectMapper objectMapper = new ObjectMapper();
    this.response = objectMapper.convertValue(response, SDS011Response.class);
    this.response.setResponseUpdate(LocalDateTime.now());
  }
}
