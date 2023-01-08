package pl.smarthouse.smartmodule.model.actors.typelibs.PIN_OUTPUT;

import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.command.CommandValidator;

import java.util.List;

@ToString(callSuper = true)
public class PinOutputCommandValidator extends CommandValidator {

  @Override
  protected void prepareValidationMap() {
    commandValidationMap.put(
        PinOutputCommandType.SET, List.of(PinOutputSetState.HIGH, PinOutputSetState.LOW));
  }
}
