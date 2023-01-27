package pl.smarthouse.smartmodule.model.actors.type.pca9685;

import pl.smarthouse.smartmodule.model.actors.command.CommandType;

public enum Pca9685CommandType implements CommandType {
  NO_ACTION,
  READ,
  WRITE_ALL_MICROSECONDS,
  WRITE_SERVO0_MICROSECONDS,
  WRITE_SERVO1_MICROSECONDS,
  WRITE_SERVO2_MICROSECONDS,
  WRITE_SERVO3_MICROSECONDS,
  WRITE_SERVO4_MICROSECONDS,
  WRITE_SERVO5_MICROSECONDS,
  WRITE_SERVO6_MICROSECONDS,
  WRITE_SERVO7_MICROSECONDS,
  WRITE_SERVO8_MICROSECONDS,
  WRITE_SERVO9_MICROSECONDS,
  WRITE_SERVO10_MICROSECONDS,
  WRITE_SERVO11_MICROSECONDS,
  WRITE_SERVO12_MICROSECONDS,
  WRITE_SERVO13_MICROSECONDS,
  WRITE_SERVO14_MICROSECONDS,
  WRITE_SERVO15_MICROSECONDS;

  @Override
  public CommandType[] findAll() {
    return Pca9685CommandType.values();
  }
}