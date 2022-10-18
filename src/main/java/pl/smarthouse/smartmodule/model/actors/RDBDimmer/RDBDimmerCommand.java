package pl.smarthouse.smartmodule.model.actors.RDBDimmer;

import pl.smarthouse.smartmodule.model.actors.Command;

public enum RDBDimmerCommand implements Command {
  NO_ACTION,
  MODE,
  POWER,
  STATE,
  INCREMENTAL,
  MSDELAY
}
