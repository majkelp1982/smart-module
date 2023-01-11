package pl.smarthouse.smartmodule.model.actors.type.Bme280;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.actor.Actor;
import pl.smarthouse.smartmodule.model.actors.command.CommandSet;
import pl.smarthouse.smartmodule.model.enums.ActorType;

import java.time.LocalDateTime;
import java.util.Map;

@Setter
@Getter
@ToString(callSuper = true)
public class Bme280 extends Actor {
  private Bme280CommandSet commandSet;
  private Bme280Response response;
  private int csPin;

  public Bme280(@NonNull final String name, final int csPin) {
    super(ActorType.BME280, name);
    this.csPin = csPin;
    setCommandSet(new Bme280CommandSet(Bme280CommandType.NO_ACTION));
  }

  @Override
  public CommandSet getCommandSet() {
    return commandSet;
  }

  @Override
  public void setResponse(final Map response) {
    final ObjectMapper objectMapper = new ObjectMapper();
    this.response = objectMapper.convertValue(response, Bme280Response.class);
    this.response.setTemperature((int) (this.response.getTemperature() * 100) / 100.00);
    this.response.setResponseUpdate(LocalDateTime.now());
  }
}
