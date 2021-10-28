package pl.smarthouse.module.sensors.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import pl.smarthouse.module.sensors.model.enums.SensorAction;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class SensorCommand {
  @NonNull private String sensorName;
  @NonNull private SensorAction action;

  public SensorCommand(
      @JsonProperty("sensorName") final String sensorName,
      @JsonProperty("action") final SensorAction action) {
    this.sensorName = sensorName;
    this.action = action;
  }
}
