package pl.smarthouse.smartmodule.model.actors.typelibs.PWM;

import pl.smarthouse.smartmodule.model.actors.command.CommandType;

public enum PWMCommandType implements CommandType {
  NO_ACTION,
  READ,
  ATTACH,
  SET_DUTY_CYCLE;

  @Override
  public CommandType[] findAll() {
    return PWMCommandType.values();
  }
}
