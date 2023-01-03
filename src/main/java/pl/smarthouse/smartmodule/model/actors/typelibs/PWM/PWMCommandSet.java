package pl.smarthouse.smartmodule.model.actors.typelibs.PWM;

import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.command.CommandSet;

@ToString(callSuper = true)
public class PWMCommandSet extends CommandSet {
  public PWMCommandSet(final PWMCommandType commandType, final String value) {
    super.setCommandType(commandType);
    super.setValue(value);
  }

  public PWMCommandSet(final PWMCommandType commandType) {
    super.setCommandType(commandType);
  }
}
