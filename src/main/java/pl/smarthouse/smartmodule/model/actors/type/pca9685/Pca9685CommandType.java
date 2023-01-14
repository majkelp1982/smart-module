package pl.smarthouse.smartmodule.model.actors.type.pca9685;

import pl.smarthouse.smartmodule.model.actors.command.CommandType;

public enum Pca9685CommandType implements CommandType {
  NO_ACTION,
  WRITE_MICROSECONDS;

  @Override
  public CommandType[] findAll() {
    return Pca9685CommandType.values();
  }
}
