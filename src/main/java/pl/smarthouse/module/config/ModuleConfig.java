package pl.smarthouse.module.config;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import pl.smarthouse.module.GPO.model.PinDao;
import pl.smarthouse.module.GPO.utils.PinModelMapper;
import pl.smarthouse.module.config.model.ModuleConfigDto;
import pl.smarthouse.module.sensors.model.SensorDao;
import pl.smarthouse.module.sensors.utils.SensorModelMapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
@ToString
@Builder(builderClassName = "Builder", buildMethodName = "build")
public class ModuleConfig {
  private static final String DATE_PATTERN = "yyyy.MM.dd";
  private static final String INVALID_DATE_PATTERN = "Invalid version format. Expected %s";
  private static final String DUPLICATED_ELEMENTS = "Duplicated elements in %s set";
  private static final String SENSOR = "sensor";
  private static final String PIN = "pin";

  private String type;

  private String version;

  private Set<PinDao> pinDaoSet;
  private Set<SensorDao> sensorDaoSet;

  public static class Builder {
    public ModuleConfig build() {
      try {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        LocalDate.parse(version, formatter);
      } catch (final Exception e) {
        throw new IllegalArgumentException(String.format(INVALID_DATE_PATTERN, DATE_PATTERN));
      }

      if (type == null) {
        throw new NullPointerException("Type can't be null");
      }

      if (pinDaoSet == null) {
        pinDaoSet = new HashSet<>();
      }

      if (sensorDaoSet == null) {
        sensorDaoSet = new HashSet<>();
      }

      checkDuplicatedPinNumbers(pinDaoSet);
      checkDuplicatedSensorNames(sensorDaoSet);

      return new ModuleConfig(type, version, pinDaoSet, sensorDaoSet);
    }
  }

  private static void checkDuplicatedPinNumbers(final Set<PinDao> pinDaoSet) {
    final HashSet<Integer> pinNumberCheckSet = new HashSet<>();
    pinDaoSet.stream()
        .filter(element -> !pinNumberCheckSet.add(element.getPinNumber()))
        .collect(Collectors.toSet());

    if (pinNumberCheckSet.size() != pinDaoSet.size()) {
      throw new IllegalArgumentException(String.format(DUPLICATED_ELEMENTS, PIN));
    }
  }

  private static void checkDuplicatedSensorNames(final Set<SensorDao> sensorDaoSet) {
    final HashSet<String> sensorNameCheckSet = new HashSet<>();
    sensorDaoSet.stream()
        .filter(element -> !sensorNameCheckSet.add(element.getName()))
        .collect(Collectors.toSet());

    if (sensorNameCheckSet.size() != sensorDaoSet.size()) {
      throw new IllegalArgumentException(String.format(DUPLICATED_ELEMENTS, SENSOR));
    }
  }

  public ModuleConfigDto getConfig() {
    return ModuleConfigDto.builder()
        .type(type)
        .version(version)
        .pinConfigDtoSet(
            pinDaoSet.stream()
                .map(pinDao -> PinModelMapper.toPinConfigDto(pinDao))
                .collect(Collectors.toSet()))
        .sensorConfigDtoSet(
            sensorDaoSet.stream()
                .map(sensorDao -> SensorModelMapper.toSensorConfigDto(sensorDao))
                .collect(Collectors.toSet()))
        .build();
  }
}
