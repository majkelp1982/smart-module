package pl.smarthouse.smartmodule.model.actors.type.pin;

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
public class Pin extends Actor {
  private PinCommandSet commandSet;
  private PinResponse response;
  private int pin;
  private PinMode pinMode;
  private PinState defaultPinState;
  private boolean defaultEnabled;

  public Pin(
      @NonNull final String name,
      final int pin,
      final PinMode pinMode,
      final PinState defaultPinState,
      final boolean defaultEnabled) {
    super(ActorType.PIN, name);
    this.pin = pin;
    this.pinMode = pinMode;
    this.defaultPinState = defaultPinState;
    this.defaultEnabled = defaultEnabled;
    setCommandSet(new PinCommandSet(PinCommandType.NO_ACTION));
  }

  @Override
  public CommandSet getCommandSet() {
    return commandSet;
  }

  @Override
  public void setResponse(final Map response) {
    final ObjectMapper objectMapper = new ObjectMapper();
    this.response = objectMapper.convertValue(response, PinResponse.class);
    this.response.setResponseUpdate(LocalDateTime.now());
  }
}
