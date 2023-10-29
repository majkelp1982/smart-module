package pl.smarthouse.smartmodule.model.actors.type.pin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import pl.smarthouse.smartmodule.exceptions.InvalidPinModeException;
import pl.smarthouse.smartmodule.model.actors.actor.Actor;
import pl.smarthouse.smartmodule.model.actors.command.CommandSet;
import pl.smarthouse.smartmodule.model.enums.ActorType;

@Setter
@Getter
@ToString(callSuper = true)
public class Pin extends Actor {
  @JsonIgnore private PinCommandSet commandSet;
  @JsonIgnore private PinResponse response;
  private int pin;
  private PinMode pinMode;
  private PinState defaultPinState;
  private boolean defaultEnabled;
  private int timebaseInSec;

  // Mode OUTPUT
  public Pin(
      @NonNull final String name,
      @NonNull final int pin,
      @NonNull final PinMode pinMode,
      final PinState defaultPinState,
      final boolean defaultEnabled) {
    super(ActorType.PIN, name);
    if (!PinMode.OUTPUT.equals(pinMode)) {
      throw new InvalidPinModeException(PinMode.OUTPUT, pinMode);
    }
    this.pin = pin;
    this.pinMode = pinMode;
    this.defaultPinState = defaultPinState;
    this.defaultEnabled = defaultEnabled;
    setCommandSet(new PinCommandSet(PinCommandType.NO_ACTION));
  }

  // Mode ANALOG_INPUT
  public Pin(@NonNull final String name, @NonNull final int pin, @NonNull final PinMode pinMode) {
    super(ActorType.PIN, name);
    if (!PinMode.ANALOG_INPUT.equals(pinMode)) {
      throw new InvalidPinModeException(PinMode.ANALOG_INPUT, pinMode);
    }
    this.pin = pin;
    this.pinMode = pinMode;
    setCommandSet(new PinCommandSet(PinCommandType.NO_ACTION));
  }

  // Mode LOW_STATE_COUNTER
  public Pin(
      @NonNull final String name,
      @NonNull final int pin,
      @NonNull final PinMode pinMode,
      @NonNull final int timebaseInSec) {
    super(ActorType.PIN, name);
    if (!PinMode.LOW_STATE_COUNTER.equals(pinMode)) {
      throw new InvalidPinModeException(PinMode.LOW_STATE_COUNTER, pinMode);
    }
    this.pin = pin;
    this.pinMode = pinMode;
    this.timebaseInSec = timebaseInSec;
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
