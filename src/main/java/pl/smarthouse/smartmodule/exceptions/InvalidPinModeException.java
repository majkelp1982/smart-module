package pl.smarthouse.smartmodule.exceptions;

import pl.smarthouse.smartmodule.model.actors.type.pin.PinMode;

public class InvalidPinModeException extends RuntimeException {
  private static final String MESSAGE = "This constructor is for pin mode: %s, not for mode: %s";

  public InvalidPinModeException(final PinMode expectedMode, final PinMode isMode) {
    super(String.format(MESSAGE, expectedMode, isMode));
  }
}
