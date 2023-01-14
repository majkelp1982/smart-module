package pl.smarthouse.smartmodule.model.actors.type.sds011;

import pl.smarthouse.smartmodule.model.actors.command.CommandSet;

public class Sds011CommandSet extends CommandSet {
  public Sds011CommandSet(final Sds011CommandType commandType) {
    super.setCommandType(commandType);
  }

  public Sds011CommandSet(final Sds011CommandType commandType, final String value) {
    super.setCommandType(commandType);
    super.setValue(value);
  }
}
