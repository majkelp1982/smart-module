package pl.smarthouse.module.sensors.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class SensorResponse {
  @NonNull private String sensorName;
  @NonNull private String response;

  public SensorResponse(
      @JsonProperty("sensorName") final String sensorName,
      @JsonProperty("response") final String response) {
    this.sensorName = sensorName;
    this.response = response;
  }
}
