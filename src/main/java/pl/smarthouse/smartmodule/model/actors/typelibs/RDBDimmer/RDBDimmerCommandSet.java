package pl.smarthouse.smartmodule.model.actors.typelibs.RDBDimmer;

import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.command.CommandSet;

@ToString(callSuper = true)
public class RDBDimmerCommandSet extends CommandSet {

  public RDBDimmerCommandSet(final RDBDimmerCommandType commandType, final String value) {
    final RDBDimmerCommandValidator rdbDimmerCommandValidator = new RDBDimmerCommandValidator();
    rdbDimmerCommandValidator.validate(commandType, value);
    super.setCommandType(commandType);
    super.setValue(value);
  }

  public RDBDimmerCommandSet(final RDBDimmerCommandType commandType) {
    final RDBDimmerCommandValidator rdbDimmerCommandValidator = new RDBDimmerCommandValidator();
    rdbDimmerCommandValidator.validate(commandType, null);
    super.setCommandType(commandType);
  }
}
