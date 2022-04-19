package pl.smarthouse.smartmodule.model.actors.BME280;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import pl.smarthouse.smartmodule.model.actors.Actor;
import pl.smarthouse.smartmodule.model.actors.Command;
import pl.smarthouse.smartmodule.model.types.ActorType;

@Setter
@Getter
public class BME280 extends Actor {
  private BME280Command command;
  private BME280Response response;

  public BME280(@NonNull final String name) {
    super(ActorType.BME280, name);
  }

  @Override
  public void setCommand(final Command command) {
    this.command = (BME280Command) command;
  }

  @Override
  public void setResponse(final String response) throws JsonProcessingException {
    final ObjectMapper objectMapper = new ObjectMapper();
    this.response = objectMapper.readValue(response, BME280Response.class);
  }
}
