package pl.smarthouse.module.sensors.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.smarthouse.module.sensors.enums.SensorType;
import pl.smarthouse.module.sensors.model.enums.SensorAction;

import java.time.LocalDateTime;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class SensorDao {
  protected static final String WRONG_ACTION = "This action is not allowed for %s sensor";
  @NonNull protected String name;
  protected SensorType type;
  @NonNull protected SensorAction action; // = SensorAction.NO_ACTION;
  int pendingTime;
  @NonNull private LocalDateTime lastActionTimeStamp = LocalDateTime.MIN;
  private String response;

  public abstract SensorConfigDto getDto();
}
