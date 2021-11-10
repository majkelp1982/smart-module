package pl.smarthouse.module.sensors.model.sensorBME280SPI;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import pl.smarthouse.module.sensors.model.SensorConfigDto;

@SuperBuilder
@Getter
public class SensorBME280SPIDto extends SensorConfigDto {
  @NonNull private int csPin;
}
