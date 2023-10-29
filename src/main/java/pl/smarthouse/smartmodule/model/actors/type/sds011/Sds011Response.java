package pl.smarthouse.smartmodule.model.actors.type.sds011;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.response.Response;

@Getter
@Setter
@ToString(callSuper = true)
public class Sds011Response extends Response {
  private Sds011State mode;
  private boolean error;
  private double pm025;
  private double pm10;
}
