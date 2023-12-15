package pl.smarthouse.smartmodule.model.actors.type.pin;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.response.Response;

@Getter
@Setter
@ToString(callSuper = true)
public class PinResponse extends Response {
  private PinState pinState;
  private PinState pinDefaultState;
  private int pinValue;
  private int counter;
}
