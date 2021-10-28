package pl.smarthouse.module.sensors.model.sensorBME280SPI;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class SensorBME280SPIResponse {
  @NonNull private float temperature;
  @NonNull private float pressure;
  @NonNull private int humidity;

  public SensorBME280SPIResponse(
      @JsonProperty("temperature") final float temperature,
      @JsonProperty("pressure") final float pressure,
      @JsonProperty("humidity") final int humidity) {
    this.temperature = temperature;
    this.pressure = pressure;
    this.humidity = humidity;
  }

  public static SensorBME280SPIResponse map(final String json) throws JsonProcessingException {
    final ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(json, SensorBME280SPIResponse.class);
  }
}
