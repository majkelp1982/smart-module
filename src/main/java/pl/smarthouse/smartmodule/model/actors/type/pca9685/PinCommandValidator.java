package pl.smarthouse.smartmodule.model.actors.type.pca9685;

import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.command.CommandValidator;

import java.util.List;

@ToString(callSuper = true)
public class PinCommandValidator extends CommandValidator {

  @Override
  protected void prepareValidationMap() {
    commandValidationMap.put(Pca9685CommandType.WRITE_MICROSECONDS, List.of(0, 4000));
  }
}
