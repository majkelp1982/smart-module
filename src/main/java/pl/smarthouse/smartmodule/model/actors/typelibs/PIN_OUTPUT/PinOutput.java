package pl.smarthouse.smartmodule.model.actors.typelibs.PIN_OUTPUT;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.actor.Actor;
import pl.smarthouse.smartmodule.model.actors.command.CommandSet;
import pl.smarthouse.smartmodule.model.types.ActorType;

import java.time.LocalDateTime;
import java.util.Map;

@Setter
@Getter
@ToString(callSuper = true)
public class PinOutput extends Actor {
  private PinOutputCommandSet commandSet;
  private PinOutputResponse response;
  private int pin;
  private PinOutputSetState defaultPinOutputState;
  private boolean defaultEnabled;

  public PinOutput(
      @NonNull final String name,
      final int pin,
      final PinOutputSetState defaultPinOutputState,
      final boolean defaultEnabled) {
    super(ActorType.PIN_OUTPUT, name);
    this.pin = pin;
    this.defaultPinOutputState = defaultPinOutputState;
    this.defaultEnabled = defaultEnabled;
    setCommandSet(new PinOutputCommandSet(PinOutputCommandType.NO_ACTION));
  }

  @Override
  public CommandSet getCommandSet() {
    return commandSet;
  }

  @Override
  public void setResponse(final Map response) {
    final ObjectMapper objectMapper = new ObjectMapper();
    this.response = objectMapper.convertValue(response, PinOutputResponse.class);
    this.response.setResponseUpdate(LocalDateTime.now());
  }
}
