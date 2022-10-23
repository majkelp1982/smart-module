package pl.smarthouse.smartmodule.model.actors.typelibs.BME280;

import pl.smarthouse.smartmodule.model.actors.command.CommandType;

public enum BME280CommandType implements CommandType {
  NO_ACTION,
  READ;

  @Override
  public CommandType[] findAll() {
    return BME280CommandType.values();
  }
}
