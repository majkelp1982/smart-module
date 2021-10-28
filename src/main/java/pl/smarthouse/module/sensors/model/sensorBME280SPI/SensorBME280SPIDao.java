package pl.smarthouse.module.sensors.model.sensorBME280SPI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import pl.smarthouse.module.sensors.enums.SensorType;
import pl.smarthouse.module.sensors.model.SensorDao;
import pl.smarthouse.module.sensors.model.enums.SensorAction;

import java.util.List;

import static pl.smarthouse.module.sensors.model.enums.SensorAction.NO_ACTION;
import static pl.smarthouse.module.sensors.model.enums.SensorAction.READ;

@SuperBuilder
@Getter
@AllArgsConstructor
public class SensorBME280SPIDao extends SensorDao {
  @NonNull private final SensorType type = SensorType.BME280SPI;
  @NonNull private int csPin;

  @Override
  public void setAction(final SensorAction action) {
    if (List.of(NO_ACTION, READ).contains(action)) {
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
}
