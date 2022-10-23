package pl.smarthouse.smartmodule.model.actors.typelibs.RDBDimmer;

import lombok.Getter;
import lombok.Setter;
import pl.smarthouse.smartmodule.model.actors.response.Response;

@Getter
@Setter
public class RDBDimmerResponse extends Response {
  private String mode;
  private boolean state;
  private int power;
  private int goalPower;
  private boolean incremental;
  private int msDelay;
}
