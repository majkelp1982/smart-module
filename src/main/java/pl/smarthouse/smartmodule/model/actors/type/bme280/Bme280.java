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
    final Bme280Response resp = objectMapper.convertValue(response, Bme280Response.class);
    resp.setTemperature((int) (resp.getTemperature() * 100) / 100.00);
    resp.setPressure(resp.getPressure() / 100.00);

    final boolean isValid = Bme280ResponseValidator.isResponseValid(name, resp);
    if (isValid) {
      this.response = resp;
      this.response.setResponseUpdate(LocalDateTime.now());
    }
    this.response.setError(!isValid);
  }
}
