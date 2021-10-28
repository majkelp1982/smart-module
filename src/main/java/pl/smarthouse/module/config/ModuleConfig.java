package pl.smarthouse.module.config;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import pl.smarthouse.module.GPO.model.PinDao;
import pl.smarthouse.module.sensors.model.SensorDao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode
@ToString
@Builder(builderClassName = "Builder", buildMethodName = "build")
public class ModuleConfig {

  private String type;

  private String version;

  private Set<PinDao> pinDaoSet;

  private Set<SensorDao> sensorDaoSet;

  public static class Builder {
    public ModuleConfig build() {
      try {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDate.parse(version, formatter);
      } catch (final Exception e) {
        throw new IllegalArgumentException("Invalid version format. Expected yyyy.MM.dd");
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

      return new ModuleConfig(type, version, pinDaoSet, sensorDaoSet);
    }
  }
}
