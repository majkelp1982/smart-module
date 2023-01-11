package pl.smarthouse.smartmodule.model.actors.type.pwm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.response.Response;

@Getter
@Setter
@ToString(callSuper = true)
public class PwmResponse extends Response {
  @JsonProperty(value = "isAttached")
  private boolean isAttached;

  @JsonProperty(value = "dutyCycle")
  private int dutyCycle;
}
