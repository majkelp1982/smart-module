package pl.smarthouse.smartmodule.model.actors.typelibs.BME280;

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
public class BME280 extends Actor {
  private BME280CommandSet commandSet;
  private BME280Response response;
  private int csPin;

  public BME280(@NonNull final String name, final int csPin) {
    super(ActorType.BME280, name);
    this.csPin = csPin;
    setCommandSet(new BME280CommandSet(BME280CommandType.NO_ACTION));
  }

  @Override
  public CommandSet getCommandSet() {
    return commandSet;
  }

  @Override
  public void setResponse(final Map response) {
    final ObjectMapper objectMapper = new ObjectMapper();
    this.response = objectMapper.convertValue(response, BME280Response.class);
    this.response.setTemperature((int) (this.response.getTemperature() * 100) / 100.00);
    this.response.setResponseUpdate(LocalDateTime.now());
  }
}
