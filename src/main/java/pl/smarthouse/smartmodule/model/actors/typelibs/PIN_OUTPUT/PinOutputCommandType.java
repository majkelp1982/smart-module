package pl.smarthouse.smartmodule.model.actors.typelibs.PIN_OUTPUT;

import pl.smarthouse.smartmodule.model.actors.command.CommandType;

public enum PinOutputCommandType implements CommandType {
  NO_ACTION,
  SET;

  @Override
  public CommandType[] findAll() {
    return PinOutputCommandType.values();
  }
}
