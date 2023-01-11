package pl.smarthouse.smartmodule.model.actors.type.pwm;

import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.command.CommandSet;

@ToString(callSuper = true)
public class PwmCommandSet extends CommandSet {
  public PwmCommandSet(final PwmCommandType commandType, final String value) {
    final PwmCommandValidator pwmCommandValidator = new PwmCommandValidator();
    pwmCommandValidator.validate(commandType, value);
    super.setCommandType(commandType);
    super.setValue(value);
  }

  public PwmCommandSet(final PwmCommandType commandType) {
    final PwmCommandValidator pwmCommandValidator = new PwmCommandValidator();
    pwmCommandValidator.validate(commandType, null);
    super.setCommandType(commandType);
  }
}
