package pl.smarthouse.module.GPO.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import pl.smarthouse.module.GPO.enums.PinAction;
import pl.smarthouse.module.GPO.enums.PinModes;

@Getter
@Builder
@EqualsAndHashCode
public class PinConfigDto {
  @NonNull private int pinNumber;
  @NonNull private PinModes mode;
  private PinAction standby;
  private int defaultLatchTime;
}
