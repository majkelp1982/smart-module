package pl.smarthouse.smartmodule.model.actors.type.BME280;

import pl.smarthouse.smartmodule.model.actors.command.CommandType;

public enum Bme280CommandType implements CommandType {
  NO_ACTION,
  READ;

  @Override
  public CommandType[] findAll() {
    return Bme280CommandType.values();
  }
}