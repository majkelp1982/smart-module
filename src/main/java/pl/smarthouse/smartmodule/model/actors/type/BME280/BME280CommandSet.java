package pl.smarthouse.smartmodule.model.actors.type.BME280;

import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.command.CommandSet;

@ToString(callSuper = true)
public class BME280CommandSet extends CommandSet {
  public BME280CommandSet(final BME280CommandType commandType, final String value) {
    super.setCommandType(commandType);
    super.setValue(value);
  }

  public BME280CommandSet(final BME280CommandType commandType) {
    super.setCommandType(commandType);
  }
}
