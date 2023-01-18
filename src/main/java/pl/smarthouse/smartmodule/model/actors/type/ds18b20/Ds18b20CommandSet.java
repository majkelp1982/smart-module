package pl.smarthouse.smartmodule.model.actors.type.ds18b20;

import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.command.CommandSet;

@ToString(callSuper = true)
public class Ds18b20CommandSet extends CommandSet {
  public Ds18b20CommandSet(final Ds18b20CommandType commandType, final String value) {
    super.setCommandType(commandType);
    super.setValue(value);
  }

  public Ds18b20CommandSet(final Ds18b20CommandType commandType) {
    super.setCommandType(commandType);
  }
}
