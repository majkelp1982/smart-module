package pl.smarthouse.module.config.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import pl.smarthouse.module.GPO.model.PinConfigDto;
import pl.smarthouse.module.sensors.model.SensorConfigDto;

import java.util.Set;

@Builder
@EqualsAndHashCode
@Getter
public class ModuleConfigDto {
  private String type;
  private String version;

  Set<PinConfigDto> pinConfigDtoSet;
  Set<SensorConfigDto> sensorConfigDtoSet;
}
