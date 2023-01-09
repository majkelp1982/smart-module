package pl.smarthouse.smartmodule.model.actors.type.PWM;

import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.command.CommandValidator;

import java.util.List;

@ToString(callSuper = true)
public class PWMCommandValidator extends CommandValidator {

  @Override
  protected void prepareValidationMap() {
    commandValidationMap.put(PWMCommandType.ATTACH, List.of("TRUE", "FALSE"));
    commandValidationMap.put(PWMCommandType.DUTY_CYCLE, List.of(0, 255));
  }
}
