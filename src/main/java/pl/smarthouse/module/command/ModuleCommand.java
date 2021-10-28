package pl.smarthouse.module.command;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import pl.smarthouse.module.GPO.model.PinCommand;
import pl.smarthouse.module.sensors.model.SensorCommand;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode
@ToString
@Builder(builderClassName = "Builder", buildMethodName = "build")
public class ModuleCommand {

  private String type;
  private String version;
  private Set<PinCommand> pinCommandSet;
  private Set<SensorCommand> sensorCommandSet;

  public static class Builder {
    public ModuleCommand build() {
      try {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDate.parse(version, formatter);
      } catch (final Exception e) {
        throw new IllegalArgumentException("Invalid version format. Expected yyyy.MM.dd");
      }

      if (type == null) {
        throw new NullPointerException("Type can't be null");
      }

      if (pinCommandSet == null) {
        pinCommandSet = new HashSet<>();
      }
      if (sensorCommandSet == null) {
        sensorCommandSet = new HashSet<>();
      }
      return new ModuleCommand(type, version, pinCommandSet, sensorCommandSet);
    }
  }
}
