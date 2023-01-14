package pl.smarthouse.smartmodule.model.actors.type.pca9685;

import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.command.CommandValidator;

import java.util.List;

@ToString(callSuper = true)
public class Pca9685CommandValidator extends CommandValidator {
  private static final int MIN_SERVO_MICROSECONDS = 0;
  private static final int MAX_SERVO_MICROSECONDS = 4000;

  @Override
  protected void prepareValidationMap() {
    commandValidationMap.put(
        Pca9685CommandType.WRITE_SERVO0_MICROSECONDS,
        List.of(MIN_SERVO_MICROSECONDS, MAX_SERVO_MICROSECONDS));
    commandValidationMap.put(
        Pca9685CommandType.WRITE_SERVO1_MICROSECONDS,
        List.of(MIN_SERVO_MICROSECONDS, MAX_SERVO_MICROSECONDS));
    commandValidationMap.put(
        Pca9685CommandType.WRITE_SERVO2_MICROSECONDS,
        List.of(MIN_SERVO_MICROSECONDS, MAX_SERVO_MICROSECONDS));
    commandValidationMap.put(
        Pca9685CommandType.WRITE_SERVO3_MICROSECONDS,
        List.of(MIN_SERVO_MICROSECONDS, MAX_SERVO_MICROSECONDS));
    commandValidationMap.put(
        Pca9685CommandType.WRITE_SERVO4_MICROSECONDS,
        List.of(MIN_SERVO_MICROSECONDS, MAX_SERVO_MICROSECONDS));
    commandValidationMap.put(
        Pca9685CommandType.WRITE_SERVO5_MICROSECONDS,
        List.of(MIN_SERVO_MICROSECONDS, MAX_SERVO_MICROSECONDS));
    commandValidationMap.put(
        Pca9685CommandType.WRITE_SERVO6_MICROSECONDS,
        List.of(MIN_SERVO_MICROSECONDS, MAX_SERVO_MICROSECONDS));
    commandValidationMap.put(
        Pca9685CommandType.WRITE_SERVO7_MICROSECONDS,
        List.of(MIN_SERVO_MICROSECONDS, MAX_SERVO_MICROSECONDS));
    commandValidationMap.put(
        Pca9685CommandType.WRITE_SERVO8_MICROSECONDS,
        List.of(MIN_SERVO_MICROSECONDS, MAX_SERVO_MICROSECONDS));
    commandValidationMap.put(
        Pca9685CommandType.WRITE_SERVO9_MICROSECONDS,
        List.of(MIN_SERVO_MICROSECONDS, MAX_SERVO_MICROSECONDS));
    commandValidationMap.put(
        Pca9685CommandType.WRITE_SERVO10_MICROSECONDS,
        List.of(MIN_SERVO_MICROSECONDS, MAX_SERVO_MICROSECONDS));
    commandValidationMap.put(
        Pca9685CommandType.WRITE_SERVO11_MICROSECONDS,
        List.of(MIN_SERVO_MICROSECONDS, MAX_SERVO_MICROSECONDS));
    commandValidationMap.put(
        Pca9685CommandType.WRITE_SERVO12_MICROSECONDS,
        List.of(MIN_SERVO_MICROSECONDS, MAX_SERVO_MICROSECONDS));
    commandValidationMap.put(
        Pca9685CommandType.WRITE_SERVO13_MICROSECONDS,
        List.of(MIN_SERVO_MICROSECONDS, MAX_SERVO_MICROSECONDS));
    commandValidationMap.put(
        Pca9685CommandType.WRITE_SERVO14_MICROSECONDS,
        List.of(MIN_SERVO_MICROSECONDS, MAX_SERVO_MICROSECONDS));
    commandValidationMap.put(
        Pca9685CommandType.WRITE_SERVO15_MICROSECONDS,
        List.of(MIN_SERVO_MICROSECONDS, MAX_SERVO_MICROSECONDS));
  }
}
