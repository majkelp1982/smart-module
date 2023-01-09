package pl.smarthouse.smartmodule.model.actors.type.pin;

import pl.smarthouse.smartmodule.model.actors.command.CommandType;

public enum PinCommandType implements CommandType {
  NO_ACTION,
  SET;

  @Override
  public CommandType[] findAll() {
    return PinCommandType.values();
  }
}
