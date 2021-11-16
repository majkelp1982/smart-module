package pl.smarthouse.module.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import pl.smarthouse.module.GPO.enums.PinDigitalState;
import pl.smarthouse.module.GPO.enums.PinModes;
import pl.smarthouse.module.GPO.model.PinDao;
import pl.smarthouse.module.GPO.model.PinResponse;
import pl.smarthouse.module.GPO.utils.PinModelMapper;
import pl.smarthouse.module.config.ModuleConfig;
import pl.smarthouse.module.config.model.ModuleConfigDto;
import pl.smarthouse.module.response.ModuleResponse;
import pl.smarthouse.module.sensors.model.SensorDao;
import pl.smarthouse.module.sensors.model.SensorResponse;

import java.util.Optional;
import java.util.stream.Collectors;

public class ModelMapper {
  private static final String OUTPUT = "OUTPUT";
  private static final String INPUT = "INPUT";
  private static final String INVALID_VERSION = "Invalid version. Is %s should be %s";
  private static final String INVALID_MODULE = "Invalid module name. Is %s should be %s";
  private static final String PIN_MISSING = "Pin number %s is missing in module configuration";
  private static final String PIN_WRONG_MODE =
      "Pin number %s is OUTPUT. Can not provide response action";
  private static final String PIN_MODE_NOT_FOUND = "Pin number %s mode not recognized";
  private static final String SENSOR_MISSING = "Sensor name %s is missing in module configuration";

  public static ModuleConfigDto getConfigDto(final ModuleConfig moduleConfig) {
    return ModuleConfigDto.builder()
        .type(moduleConfig.getType())
        .version(moduleConfig.getVersion())
        .pinConfigDtoSet(
            moduleConfig.getPinDaoSet().stream()
                .map(pinDao -> PinModelMapper.toPinConfigDto(pinDao))
                .collect(Collectors.toSet()))
        .sensorConfigDtoSet(
            moduleConfig.getSensorDaoSet().stream()
                .map(sensorDao -> sensorDao.getDto())
                .collect(Collectors.toSet()))
        .build();
  }

  public static void copyResponseData(
      final ModuleConfig moduleConfig, final ModuleResponse moduleResponse)
      throws JsonProcessingException {

    validation(moduleConfig, moduleResponse);
    copyToPinDao(moduleConfig, moduleResponse);
    copyToSensorDao(moduleConfig, moduleResponse);
  }

  public static PinDao findPin(final ModuleConfig moduleConfig, final int pinNumber) {
    final Optional<PinDao> optionalPinDao =
        moduleConfig.getPinDaoSet().stream()
            .filter(pinDao -> (pinDao.getPinNumber() == pinNumber))
            .findFirst();
    if (optionalPinDao.isEmpty()) {
      throw new IllegalArgumentException(String.format(PIN_MISSING, pinNumber));
    }
    return optionalPinDao.get();
  }

  public static SensorDao findSensor(final ModuleConfig moduleConfig, final String sensorName) {
    final Optional<SensorDao> optionalSensorDao =
        moduleConfig.getSensorDaoSet().stream()
            .filter(sensorDao -> (sensorDao.getName().equals(sensorName)))
            .findFirst();
    if (optionalSensorDao.isEmpty()) {
      throw new IllegalArgumentException(String.format(SENSOR_MISSING, sensorName));
    }
    return optionalSensorDao.get();
  }

  private static void copyToPinDao(
      final ModuleConfig moduleConfig, final ModuleResponse moduleResponse) {
    for (final PinResponse pinResponse : moduleResponse.getPinResponseSet()) {
      final Optional<PinDao> optionalPinDao =
          moduleConfig.getPinDaoSet().stream()
              .filter(pinDao -> (pinResponse.getPinNumber() == pinDao.getPinNumber()))
              .findFirst();
      if (optionalPinDao.isEmpty()) {
        throw new IllegalArgumentException(String.format(PIN_MISSING, pinResponse.getPinNumber()));
      }

      final PinDao pin = optionalPinDao.get();

      if (pin.getMode().toString().contains(OUTPUT)) {
        throw new IllegalArgumentException(
            String.format(PIN_WRONG_MODE, pinResponse.getPinNumber()));
      }

      if (pin.getMode().equals(PinModes.ANALOG)) {
        pin.setAnalogState(pinResponse.getResponse());
        continue;
      }

      if (pin.getMode().toString().contains(INPUT)) {
        pin.setDigitalState(
            pinResponse.getResponse() == 0 ? PinDigitalState.LOW : PinDigitalState.HIGH);
        continue;
      }
      throw new IllegalArgumentException(
          String.format(PIN_MODE_NOT_FOUND, pinResponse.getPinNumber()));
    }
  }

  private static void copyToSensorDao(
      final ModuleConfig moduleConfig, final ModuleResponse moduleResponse)
      throws JsonProcessingException {
    for (final SensorResponse sensorResponse : moduleResponse.getSensorResponseSet()) {
      final Optional<SensorDao> optionalSensorDao =
          moduleConfig.getSensorDaoSet().stream()
              .filter(sensorDao -> (sensorResponse.getSensorName().equals(sensorDao.getName())))
              .findFirst();

      if (optionalSensorDao.isEmpty()) {
        throw new IllegalArgumentException(
            String.format(SENSOR_MISSING, sensorResponse.getSensorName()));
      }

      final SensorDao sensor = optionalSensorDao.get();

      sensor.setResponse(sensor.map(sensorResponse.getResponse()));
    }
  }

  private static void validation(
      final ModuleConfig moduleConfig, final ModuleResponse moduleResponse)
      throws IllegalArgumentException {
    if (!moduleConfig.getVersion().equals(moduleResponse.getVersion())) {
      throw new IllegalArgumentException(
          String.format(INVALID_VERSION, moduleResponse.getVersion(), moduleConfig.getVersion()));
    }
    if (!moduleConfig.getType().equals(moduleResponse.getType())) {
      throw new IllegalArgumentException(
          String.format(INVALID_MODULE, moduleResponse.getType(), moduleConfig.getType()));
    }
  }
}
