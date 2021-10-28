package pl.smarthouse.module.GPO.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import pl.smarthouse.module.GPO.enums.PinAction;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class PinCommand {
  @NonNull private int pinNumber;
  @NonNull private PinAction action;

  public PinCommand(
      @JsonProperty("pinNumber") final int pinNumber,
      @JsonProperty("action") final PinAction action) {
    this.pinNumber = pinNumber;
    this.action = action;
  }
}
