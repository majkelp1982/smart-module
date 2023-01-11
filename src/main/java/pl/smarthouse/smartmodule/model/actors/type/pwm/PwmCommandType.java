package pl.smarthouse.smartmodule.model.actors.type.pwm;

import pl.smarthouse.smartmodule.model.actors.command.CommandType;

public enum PwmCommandType implements CommandType {
  NO_ACTION,
  READ,
  ATTACH,
  DUTY_CYCLE;

  @Override
  public CommandType[] findAll() {
    return PwmCommandType.values();
  }
}
