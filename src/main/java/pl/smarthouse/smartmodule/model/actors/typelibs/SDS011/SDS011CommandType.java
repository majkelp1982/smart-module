package pl.smarthouse.smartmodule.model.actors.typelibs.SDS011;

import pl.smarthouse.smartmodule.model.actors.command.CommandType;

public enum SDS011CommandType implements CommandType {
  NO_ACTION,
  SLEEP,
  WAKEUP,
  READ;

  @Override
  public CommandType[] findAll() {
    return SDS011CommandType.values();
  }
}
