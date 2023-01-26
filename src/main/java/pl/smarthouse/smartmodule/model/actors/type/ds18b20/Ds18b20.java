package pl.smarthouse.smartmodule.model.actors.type.ds18b20;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.actor.Actor;
import pl.smarthouse.smartmodule.model.actors.command.CommandSet;
import pl.smarthouse.smartmodule.model.enums.ActorType;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Setter
@Getter
@ToString(callSuper = true)
public class Ds18b20 extends Actor {
  @JsonIgnore private Ds18b20CommandSet commandSet;
  @JsonIgnore private Ds18b20Response response = new Ds18b20Response();
  @JsonIgnore private Map<String, Ds18b20CompFactor> ds18b20CompFactorMap = new HashMap();
  private int dsPin;

  public Ds18b20(@NonNull final String name, final int dsPin) {
    super(ActorType.DS18B20, name);
    this.dsPin = dsPin;
    setCommandSet(new Ds18b20CommandSet(Ds18b20CommandType.NO_ACTION));
  }

  @Override
  public CommandSet getCommandSet() {
    return commandSet;
  }

  @Override
  public void setResponse(final Map response) {
    final ObjectMapper objectMapper = new ObjectMapper();
    final Ds18b20Response newResponse = objectMapper.convertValue(response, Ds18b20Response.class);

    newResponse.getResultSet().stream()
        .forEach(
            ds18b20Result -> {
              Ds18b20ResponseValidator.isResultValid(
                  name, ds18b20Result, ds18b20CompFactorMap.get(ds18b20Result.getAddress()));
              getDs18b20Result(ds18b20Result.getAddress())
                  .ifPresentOrElse(
                      sensor -> {
                        if (ds18b20Result.isError()) {
                          sensor.setError(true);
                        } else {
                          this.response.getResultSet().remove(sensor);
                          this.response.getResultSet().add(ds18b20Result);
                        }
                      },
                      () -> this.response.getResultSet().add(ds18b20Result));
            });
    this.response.setResponseUpdate(LocalDateTime.now());
  }

  private Optional<Ds18b20Result> getDs18b20Result(final String address) {
    return this.response.getResultSet().stream()
        .filter(ds18b20Result -> ds18b20Result.getAddress().equals(address))
        .findFirst();
  }
}
