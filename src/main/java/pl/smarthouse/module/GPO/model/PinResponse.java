package pl.smarthouse.module.GPO.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class PinResponse {
  @NonNull private int pinNumber;
  @NonNull private int response;

  public PinResponse(
      @JsonProperty("pinNumber") final int pinNumber,
      @JsonProperty("response") final int response) {
    this.pinNumber = pinNumber;
    this.response = response;
  }
}
