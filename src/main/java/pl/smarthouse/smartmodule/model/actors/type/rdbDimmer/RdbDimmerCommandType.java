package pl.smarthouse.smartmodule.model.actors.type.rdbDimmer;

import pl.smarthouse.smartmodule.model.actors.command.CommandType;

public enum RdbDimmerCommandType implements CommandType {
  NO_ACTION,
  MODE,
  POWER,
  STATE,
  INCREMENTAL,
  MSDELAY,
  READ;

  @Override
  public CommandType[] findAll() {
    return RdbDimmerCommandType.values();
  }
}
