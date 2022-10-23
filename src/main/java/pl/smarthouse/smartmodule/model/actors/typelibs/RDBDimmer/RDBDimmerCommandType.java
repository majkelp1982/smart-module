package pl.smarthouse.smartmodule.model.actors.typelibs.RDBDimmer;

import pl.smarthouse.smartmodule.model.actors.command.CommandType;

public enum RDBDimmerCommandType implements CommandType {
  NO_ACTION,
  MODE,
  POWER,
  STATE,
  INCREMENTAL,
  MSDELAY,
  READ;

  @Override
  public CommandType[] findAll() {
    return RDBDimmerCommandType.values();
  }
}
