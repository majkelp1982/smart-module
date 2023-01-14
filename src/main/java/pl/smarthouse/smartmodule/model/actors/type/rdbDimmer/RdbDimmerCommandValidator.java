package pl.smarthouse.smartmodule.model.actors.type.rdbDimmer;

import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.command.CommandValidator;

import java.util.List;

@ToString(callSuper = true)
public class RdbDimmerCommandValidator extends CommandValidator {

  @Override
  protected void prepareValidationMap() {
    commandValidationMap.put(RdbDimmerCommandType.MODE, List.of("NORMAL_MODE", "TOGGLE_MODE"));
    commandValidationMap.put(RdbDimmerCommandType.INCREMENTAL, List.of("true", "false"));
    commandValidationMap.put(RdbDimmerCommandType.STATE, List.of("ON", "OFF"));
    commandValidationMap.put(RdbDimmerCommandType.POWER, List.of(0, 100));
    commandValidationMap.put(RdbDimmerCommandType.MSDELAY, List.of(0, 1000));
  }
}
