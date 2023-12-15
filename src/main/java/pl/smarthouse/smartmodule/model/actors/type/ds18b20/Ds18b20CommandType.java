package pl.smarthouse.smartmodule.model.actors.type.ds18b20;

import pl.smarthouse.smartmodule.model.actors.command.CommandType;

public enum Ds18b20CommandType implements CommandType {
  NO_ACTION,
  READ_ADDRESSES,
  READ;

  @Override
  public CommandType[] findAll() {
    return Ds18b20CommandType.values();
  }
}
