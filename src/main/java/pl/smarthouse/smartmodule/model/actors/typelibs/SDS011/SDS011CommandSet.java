package pl.smarthouse.smartmodule.model.actors.typelibs.SDS011;

import pl.smarthouse.smartmodule.model.actors.command.CommandSet;

public class SDS011CommandSet extends CommandSet {
  public SDS011CommandSet(final SDS011CommandType commandType) {
    super.setCommandType(commandType);
  }

  public SDS011CommandSet(final SDS011CommandType commandType, final String value) {
    super.setCommandType(commandType);
    super.setValue(value);
  }
}
