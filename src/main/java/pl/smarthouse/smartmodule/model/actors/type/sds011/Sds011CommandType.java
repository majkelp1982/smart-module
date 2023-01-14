package pl.smarthouse.smartmodule.model.actors.type.sds011;

import pl.smarthouse.smartmodule.model.actors.command.CommandType;

public enum Sds011CommandType implements CommandType {
  NO_ACTION,
  SLEEP,
  WAKEUP,
  READ;

  @Override
  public CommandType[] findAll() {
    return Sds011CommandType.values();
  }
}
