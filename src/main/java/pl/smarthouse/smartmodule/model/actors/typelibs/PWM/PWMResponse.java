package pl.smarthouse.smartmodule.model.actors.typelibs.PWM;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.response.Response;

@Getter
@Setter
@ToString(callSuper = true)
public class PWMResponse extends Response {
  private boolean isAttached;
  private int dutyCycle;
}
