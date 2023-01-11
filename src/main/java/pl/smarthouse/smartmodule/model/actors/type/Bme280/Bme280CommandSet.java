package pl.smarthouse.smartmodule.model.actors.type.Bme280;

import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.command.CommandSet;

@ToString(callSuper = true)
public class Bme280CommandSet extends CommandSet {
  public Bme280CommandSet(final Bme280CommandType commandType, final String value) {
    super.setCommandType(commandType);
    super.setValue(value);
  }

  public Bme280CommandSet(final Bme280CommandType commandType) {
    super.setCommandType(commandType);
  }
}
