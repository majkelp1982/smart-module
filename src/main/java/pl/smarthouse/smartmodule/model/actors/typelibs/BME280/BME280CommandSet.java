package pl.smarthouse.smartmodule.model.actors.typelibs.BME280;

import pl.smarthouse.smartmodule.model.actors.command.CommandSet;

public class BME280CommandSet extends CommandSet {
  public BME280CommandSet(final BME280CommandType commandType, final String value) {
    super.setCommandType(commandType);
    super.setValue(value);
  }

  public BME280CommandSet(final BME280CommandType commandType) {
    super.setCommandType(commandType);
  }
}
