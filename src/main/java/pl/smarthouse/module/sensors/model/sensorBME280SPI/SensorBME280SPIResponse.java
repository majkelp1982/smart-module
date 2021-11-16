package pl.smarthouse.module.sensors.model.sensorBME280SPI;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import pl.smarthouse.module.sensors.model.SensorResponseMap;

@Builder
@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class SensorBME280SPIResponse implements SensorResponseMap {
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

  @Override
  public SensorResponseMap map(final String json) throws JsonProcessingException {
    final ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(json, SensorBME280SPIResponse.class);
  }
}
