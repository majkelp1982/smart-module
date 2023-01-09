package pl.smarthouse.smartmodule.model.actors.type.pin;

import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.command.CommandSet;

@ToString(callSuper = true)
public class PinCommandSet extends CommandSet {
  public PinCommandSet(final PinCommandType commandType, final String value) {
    super.setCommandType(commandType);
    super.setValue(value);
  }

  public PinCommandSet(final PinCommandType commandType) {
    super.setCommandType(commandType);
  }
}
