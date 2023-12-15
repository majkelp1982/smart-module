package pl.smarthouse.smartmodule.model.actors.type.pin;

import java.util.List;
import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.command.CommandValidator;

@ToString(callSuper = true)
public class PinCommandValidator extends CommandValidator {

  @Override
  protected void prepareValidationMap() {
    commandValidationMap.put(PinCommandType.SET, List.of(PinState.HIGH, PinState.LOW));
    commandValidationMap.put(
        PinCommandType.SET_DEFAULT_STATE, List.of(PinState.HIGH, PinState.LOW));
  }
}
