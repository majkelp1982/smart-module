package pl.smarthouse.smartmodule.model.actors.type.pwm;

import java.util.List;
import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.command.CommandValidator;

@ToString(callSuper = true)
public class PwmCommandValidator extends CommandValidator {

  @Override
  protected void prepareValidationMap() {
    commandValidationMap.put(PwmCommandType.ATTACH, List.of("TRUE", "FALSE"));
    commandValidationMap.put(PwmCommandType.DUTY_CYCLE, List.of(0, 255));
  }
}
