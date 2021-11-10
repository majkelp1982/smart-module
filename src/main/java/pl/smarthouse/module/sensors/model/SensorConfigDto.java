package pl.smarthouse.module.sensors.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import pl.smarthouse.module.sensors.enums.SensorType;

@SuperBuilder
@Getter
public class SensorConfigDto {
  @NonNull protected String name;
  @NonNull SensorType type;
}
