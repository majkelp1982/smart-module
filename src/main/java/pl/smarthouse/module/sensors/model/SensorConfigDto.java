package pl.smarthouse.module.sensors.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import pl.smarthouse.module.sensors.enums.SensorType;

@Getter
@Builder
public class SensorConfigDto {
  @NonNull private String name;
  @NonNull private SensorType type;
}
