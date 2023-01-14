package pl.smarthouse.smartmodule.model.actors.type.rdbDimmer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.response.Response;

@Getter
@Setter
@ToString(callSuper = true)
public class RdbDimmerResponse extends Response {
  private String mode;
  private boolean state;
  private int power;
  private int goalPower;
  private boolean incremental;
  private int msDelay;
}
