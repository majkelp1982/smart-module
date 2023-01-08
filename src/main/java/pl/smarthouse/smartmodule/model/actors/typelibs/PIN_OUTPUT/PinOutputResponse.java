package pl.smarthouse.smartmodule.model.actors.typelibs.PIN_OUTPUT;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.response.Response;

@Getter
@Setter
@ToString(callSuper = true)
public class PinOutputResponse extends Response {
  private PinOutputSetState pinOutputSetState;
}
