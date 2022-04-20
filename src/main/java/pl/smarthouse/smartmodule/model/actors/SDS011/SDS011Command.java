package pl.smarthouse.smartmodule.model.actors.SDS011;

import pl.smarthouse.smartmodule.model.actors.Command;

public enum SDS011Command implements Command {
  NO_ACTION,
  SLEEP,
  WAKEUP,
  READ
}
