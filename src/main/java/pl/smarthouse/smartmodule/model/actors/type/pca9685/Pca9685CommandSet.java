package pl.smarthouse.smartmodule.model.actors.type.pca9685;

import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.command.CommandSet;

@ToString(callSuper = true)
public class Pca9685CommandSet extends CommandSet {
  public Pca9685CommandSet(final Pca9685CommandType commandType, final String value) {
    final Pca9685CommandValidator pca9685CommandValidator = new Pca9685CommandValidator();
    pca9685CommandValidator.validate(commandType, value);
    super.setCommandType(commandType);
    super.setValue(value);
  }

  public Pca9685CommandSet(final Pca9685CommandType commandType) {
    final Pca9685CommandValidator pca9685CommandValidator = new Pca9685CommandValidator();
    pca9685CommandValidator.validate(commandType, null);
    super.setCommandType(commandType);
  }
}
