package pl.smarthouse.module.utils;

import pl.smarthouse.module.GPO.model.PinCommand;
import pl.smarthouse.module.GPO.utils.PinModelMapper;
import pl.smarthouse.module.command.ModuleCommand;
import pl.smarthouse.module.config.ModuleConfig;
import pl.smarthouse.module.sensors.model.SensorCommand;
import pl.smarthouse.module.sensors.utils.SensorModelMapper;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class CommandUtils {

  public static ModuleCommand getCommandBody(final ModuleConfig moduleConfig) {
    final LocalDateTime currentTime = LocalDateTime.now();
    final Set<PinCommand> pinActionSet =
        moduleConfig.getPinDaoSet().stream()
            .filter(
                pinDao ->
                    currentTime.isAfter(
                        pinDao.getLastActionTimeStamp().plusSeconds(pinDao.getPendingTime())))
            .map(
                pinDao -> {
                  pinDao.setLastActionTimeStamp(currentTime);
                  return pinDao;
                })
            .map(pinDao -> PinModelMapper.toPinCommand(pinDao))
            .collect(Collectors.toSet());

    final Set<SensorCommand> sensorActionSet =
        moduleConfig.getSensorDaoSet().stream()
            .filter(
                sensorDao ->
                    currentTime.isAfter(
                        sensorDao.getLastActionTimeStamp().plusSeconds(sensorDao.getPendingTime())))
            .map(
                sensorDao -> {
                  sensorDao.setLastActionTimeStamp(currentTime);
                  return sensorDao;
                })
            .map(sensorDao -> SensorModelMapper.toSensorCommand(sensorDao))
            .collect(Collectors.toSet());

    return ModuleCommand.builder()
        .type(moduleConfig.getType())
        .version(moduleConfig.getVersion())
        .pinCommandSet(pinActionSet)
        .sensorCommandSet(sensorActionSet)
        .build();
  }
}
