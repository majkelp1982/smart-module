package pl.smarthouse.smartmodule.model.actors.typelibs.PIN_OUTPUT;

import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.command.CommandSet;

@ToString(callSuper = true)
public class PinOutputCommandSet extends CommandSet {
  public PinOutputCommandSet(final PinOutputCommandType commandType, final String value) {
    super.setCommandType(commandType);
    super.setValue(value);
  }

  public PinOutputCommandSet(final PinOutputCommandType commandType) {
    super.setCommandType(commandType);
  }
}
