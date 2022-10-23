package pl.smarthouse.smartmodule.model.actors.typelibs.RDBDimmer;

import pl.smarthouse.smartmodule.model.actors.command.CommandValidator;

import java.util.List;

public class RDBDimmerCommandValidator extends CommandValidator {

  @Override
  protected void prepareValidationMap() {
    commandValidationMap.put(RDBDimmerCommandType.MODE, List.of("NORMAL_MODE", "TOGGLE_MODE"));
    commandValidationMap.put(RDBDimmerCommandType.INCREMENTAL, List.of("true", "false"));
    commandValidationMap.put(RDBDimmerCommandType.STATE, List.of("ON", "OFF"));
    commandValidationMap.put(RDBDimmerCommandType.POWER, List.of(0, 100));
    commandValidationMap.put(RDBDimmerCommandType.MSDELAY, List.of(0, 1000));
  }
}