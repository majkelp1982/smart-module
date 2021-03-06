package pl.smarthouse.module.sensors.model.sensorBME280SPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import pl.smarthouse.module.sensors.enums.SensorType;
import pl.smarthouse.module.sensors.model.SensorConfigDto;
import pl.smarthouse.module.sensors.model.SensorDao;
import pl.smarthouse.module.sensors.model.SensorResponseMap;
import pl.smarthouse.module.sensors.model.enums.SensorAction;

import java.util.List;

import static pl.smarthouse.module.sensors.model.enums.SensorAction.READ;

@SuperBuilder
@Getter
public class SensorBME280SPIDao extends SensorDao implements SensorResponseMap {
  @NonNull private final SensorType type = SensorType.BME280SPI;
  @NonNull private int csPin;

  @Override
  public void setAction(final SensorAction action) {
    if (List.of(READ).contains(action)) {
      this.action = action;
    } else {
      throw new IllegalArgumentException(String.format(WRONG_ACTION, type));
    }
  }

  @Override
  public SensorAction getAction() {
    return action;
  }

  @Override
  public void setName(final String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public SensorConfigDto getDto() {
    return SensorBME280SPIDto.builder().name(name).type(type).csPin(csPin).build();
  }

  @Override
  public SensorResponseMap map(final String json) throws JsonProcessingException {
    final SensorBME280SPIResponse sensorBME280SPIResponse = new SensorBME280SPIResponse();
    return sensorBME280SPIResponse.map(json);
  }
}
