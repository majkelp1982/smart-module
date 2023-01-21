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
import java.util.Map;

@Setter
@Getter
@ToString(callSuper = true)
public class Ds18b20 extends Actor {
  @JsonIgnore private Ds18b20CommandSet commandSet;
  @JsonIgnore private Ds18b20Response response;
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
    this.response = objectMapper.convertValue(response, Ds18b20Response.class);
    this.response.setResponseUpdate(LocalDateTime.now());
  }
}
