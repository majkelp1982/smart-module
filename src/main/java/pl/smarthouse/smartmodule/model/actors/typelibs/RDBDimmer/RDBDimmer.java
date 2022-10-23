package pl.smarthouse.smartmodule.model.actors.typelibs.RDBDimmer;

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
public class RDBDimmer extends Actor {
  private RDBDimmerCommandSet commandSet;
  private RDBDimmerResponse response;
  private int outputPin;
  private int crossZeroPin;

  public RDBDimmer(@NonNull final String name, final int outputPin, final int crossZeroPin) {
    super(ActorType.DIMMER, name);
    this.outputPin = outputPin;
    this.crossZeroPin = crossZeroPin;
    setCommandSet(new RDBDimmerCommandSet(RDBDimmerCommandType.NO_ACTION));
  }

  @Override
  public CommandSet getCommandSet() {
    return commandSet;
  }

  @Override
  public void setResponse(final Map response) {
    final ObjectMapper objectMapper = new ObjectMapper();
    this.response = objectMapper.convertValue(response, RDBDimmerResponse.class);
    this.response.setResponseUpdate(LocalDateTime.now());
  }
}
