package pl.smarthouse.smartmodule.model.actors.type.RdbDimmer;

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
public class RbdDimmer extends Actor {
  private RdbDimmerCommandSet commandSet;
  private RdbDimmerResponse response;
  private int outputPin;
  private int crossZeroPin;

  public RbdDimmer(@NonNull final String name, final int outputPin, final int crossZeroPin) {
    super(ActorType.DIMMER, name);
    this.outputPin = outputPin;
    this.crossZeroPin = crossZeroPin;
    setCommandSet(new RdbDimmerCommandSet(RdbDimmerCommandType.NO_ACTION));
  }

  @Override
  public CommandSet getCommandSet() {
    return commandSet;
  }

  @Override
  public void setResponse(final Map response) {
    final ObjectMapper objectMapper = new ObjectMapper();
    this.response = objectMapper.convertValue(response, RdbDimmerResponse.class);
    this.response.setResponseUpdate(LocalDateTime.now());
  }
}
