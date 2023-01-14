package pl.smarthouse.smartmodule.model.actors.type.pca9685;

import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.command.CommandSet;

@ToString(callSuper = true)
public class Pca9685CommandSet extends CommandSet {
  public Pca9685CommandSet(final Pca9685CommandType commandType, final String value) {
    super.setCommandType(commandType);
    super.setValue(value);
  }

  public Pca9685CommandSet(final Pca9685CommandType commandType) {
    super.setCommandType(commandType);
  }
}
