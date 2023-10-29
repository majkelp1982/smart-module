package pl.smarthouse.smartmodule.model.actors.type.sds011;

import java.util.List;
import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.command.CommandValidator;

@ToString(callSuper = true)
public class Sds011CommandValidator extends CommandValidator {

  @Override
  protected void prepareValidationMap() {
    commandValidationMap.put(
        Sds011CommandType.MODE, List.of(Sds011State.SLEEP, Sds011State.WAKEUP));
  }
}
