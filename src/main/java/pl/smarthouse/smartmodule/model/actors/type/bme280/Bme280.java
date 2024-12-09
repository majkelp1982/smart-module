package pl.smarthouse.smartmodule.model.actors.type.bme280;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.actor.Actor;
import pl.smarthouse.smartmodule.model.actors.command.CommandSet;
import pl.smarthouse.smartmodule.model.enums.ActorType;

@Setter
@Getter
@ToString(callSuper = true)
public class Bme280 extends Actor {
  @JsonIgnore private Bme280CommandSet commandSet;
  @JsonIgnore private Bme280Response response = new Bme280Response();
  private int csPin;
  int i2cAddress;
  private Bme280Mode bme280Mode;

  public Bme280(@NonNull final String name, final int csPin) {
    super(ActorType.BME280, name);
    this.csPin = csPin;
    this.bme280Mode = Bme280Mode.SPI;
    setCommandSet(new Bme280CommandSet(Bme280CommandType.NO_ACTION));
  }

  public Bme280(@NonNull final String name, Bme280Mode mode, final int i2cAddress) {
    super(ActorType.BME280, name);
    this.i2cAddress = i2cAddress;
    this.bme280Mode = mode;
    setCommandSet(new Bme280CommandSet(Bme280CommandType.NO_ACTION));
  }

  @Override
  public CommandSet getCommandSet() {
    return commandSet;
  }

  @Override
  public void setResponse(final Map response) {
    final ObjectMapper objectMapper = new ObjectMapper();
    final Bme280Response resp = objectMapper.convertValue(response, Bme280Response.class);
    resp.setTemperature((int) (resp.getTemperature() * 100) / 100.00);
    resp.setPressure(resp.getPressure() / 100.00);
    resp.setError(this.response.isError());
    resp.setResponseUpdate(LocalDateTime.now());
    this.response = resp;
  }
}
