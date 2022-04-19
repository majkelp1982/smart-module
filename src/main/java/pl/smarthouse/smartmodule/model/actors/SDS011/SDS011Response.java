package pl.smarthouse.smartmodule.model.actors.SDS011;

import lombok.Getter;
import lombok.Setter;
import pl.smarthouse.smartmodule.model.actors.Response;

@Getter
@Setter
public class SDS011Response extends Response {
  private int pm025;
  private int pm10;
}
