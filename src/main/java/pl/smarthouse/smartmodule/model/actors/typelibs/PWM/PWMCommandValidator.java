package pl.smarthouse.smartmodule.model.actors.typelibs.PWM;

import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.command.CommandValidator;
import pl.smarthouse.smartmodule.model.actors.typelibs.RDBDimmer.RDBDimmerCommandType;

import java.util.List;

@ToString(callSuper = true)
public class PWMCommandValidator extends CommandValidator {

  @Override
  protected void prepareValidationMap() {
    commandValidationMap.put(PWMCommandType.ATTACH, List.of("TRUE", "FALSE"));
  }
}
