package pl.smarthouse.smartmodule.model.actors.type.Sds011;

import lombok.Getter;
import lombok.Setter;
import pl.smarthouse.smartmodule.model.actors.response.Response;

@Getter
@Setter
public class Sds011Response extends Response {
  private int pm025;
  private int pm10;
}
