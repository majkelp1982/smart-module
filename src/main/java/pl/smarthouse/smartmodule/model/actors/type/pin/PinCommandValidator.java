package pl.smarthouse.smartmodule.model.actors.type.pin;

import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.command.CommandValidator;

import java.util.List;

@ToString(callSuper = true)
public class PinCommandValidator extends CommandValidator {

  @Override
  protected void prepareValidationMap() {
    commandValidationMap.put(PinCommandType.SET, List.of(PinState.HIGH, PinState.LOW));
  }
}
