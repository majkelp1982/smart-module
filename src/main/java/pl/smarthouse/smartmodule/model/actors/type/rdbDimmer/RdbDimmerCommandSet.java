package pl.smarthouse.smartmodule.model.actors.type.rdbDimmer;

import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.command.CommandSet;

@ToString(callSuper = true)
public class RdbDimmerCommandSet extends CommandSet {

  public RdbDimmerCommandSet(final RdbDimmerCommandType commandType, final String value) {
    final RdbDimmerCommandValidator rdbDimmerCommandValidator = new RdbDimmerCommandValidator();
    rdbDimmerCommandValidator.validate(commandType, value);
    super.setCommandType(commandType);
    super.setValue(value);
  }

  public RdbDimmerCommandSet(final RdbDimmerCommandType commandType) {
    final RdbDimmerCommandValidator rdbDimmerCommandValidator = new RdbDimmerCommandValidator();
    rdbDimmerCommandValidator.validate(commandType, null);
    super.setCommandType(commandType);
  }
}
