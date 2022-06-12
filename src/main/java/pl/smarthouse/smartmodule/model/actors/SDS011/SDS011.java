package pl.smarthouse.smartmodule.model.actors.SDS011;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import pl.smarthouse.smartmodule.model.actors.Actor;
import pl.smarthouse.smartmodule.model.types.ActorType;

import java.time.LocalDateTime;

@Setter
@Getter
public class SDS011 extends Actor {
  private SDS011Command command;
  private SDS011Response response;

  public SDS011(@NonNull final String name) {
    super(ActorType.SDS011, name);
    setCommand(SDS011Command.NO_ACTION);
  }

  public void setCommand(final SDS011Command command) {
    this.command = command;
  }

  @Override
  public void setResponse(final String response) throws JsonProcessingException {
    final ObjectMapper objectMapper = new ObjectMapper();
    this.response = objectMapper.readValue(response, SDS011Response.class);
    this.response.setResponseUpdate(LocalDateTime.now());
  }
}
