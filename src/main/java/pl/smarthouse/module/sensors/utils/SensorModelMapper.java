package pl.smarthouse.module.sensors.utils;

import pl.smarthouse.module.sensors.model.SensorCommand;
import pl.smarthouse.module.sensors.model.SensorConfigDto;
import pl.smarthouse.module.sensors.model.SensorDao;

public class SensorModelMapper {
  private static final String WRONG_INPUT_ACTION = "Pin number %s is input. Can't run %s action";
  private static final String WRONG_DIGITAL_ACTION = "Pin number %s is output. Can't run %s action";
  private static final String WRONG_ANALOG_ACTION = "Pin number %s is analog. Can't run %s action";
  private static final String VALIDATION_FAILED = "Validation failed. Mode %s not recognized";

  public static SensorCommand toSensorCommand(final SensorDao sensorDao) {
    return SensorCommand.builder()
        .sensorName(sensorDao.getName())
        .action(sensorDao.getAction())
        .build();
  }

  public static SensorConfigDto toSensorConfigDto(final SensorDao sensorDao) {
    return SensorConfigDto.builder().name(sensorDao.getName()).type(sensorDao.getType()).build();
  }
}
