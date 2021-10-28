package pl.smarthouse.module.GPO.utils;

import pl.smarthouse.module.GPO.enums.PinAction;
import pl.smarthouse.module.GPO.enums.PinModes;
import pl.smarthouse.module.GPO.model.PinCommand;
import pl.smarthouse.module.GPO.model.PinDao;

import java.util.List;

public class PinModelMapper {
  private static final String WRONG_INPUT_ACTION = "Pin number %s is input. Can't run %s action";
  private static final String WRONG_DIGITAL_ACTION = "Pin number %s is output. Can't run %s action";
  private static final String WRONG_ANALOG_ACTION = "Pin number %s is analog. Can't run %s action";
  private static final String VALIDATION_FAILED = "Validation failed. Mode %s not recognized";

  public static PinCommand toPinCommand(final PinDao pinDao) {
    validation(pinDao);
    return PinCommand.builder().pinNumber(pinDao.getPinNumber()).action(pinDao.getAction()).build();
  }

  private static void validation(final PinDao pinDao) {
    // input
    if (List.of(PinModes.INPUT, PinModes.INPUT_PULLUP, PinModes.INPUT_PULLDOWN)
        .contains(pinDao.getMode())) {
      if (!PinAction.READ.equals(pinDao.getAction())) {
        throw new IllegalArgumentException(
            String.format(WRONG_INPUT_ACTION, pinDao.getPinNumber(), pinDao.getAction()));
      }
      return;
    }

    // output
    if (List.of(PinModes.OUTPUT, PinModes.INPUT_PULLDOWN, PinModes.INPUT_PULLDOWN)
        .contains(pinDao.getMode())) {
      if (!List.of(PinAction.LOW, PinAction.HIGH).contains(pinDao.getAction())) {
        throw new IllegalArgumentException(
            String.format(WRONG_DIGITAL_ACTION, pinDao.getPinNumber(), pinDao.getAction()));
      }
      return;
    }
    // analog
    if (List.of(PinModes.ANALOG).contains(pinDao.getMode())) {
      if (!List.of(PinAction.READ_ANALOG, PinAction.WRITE_ANALOG).contains(pinDao.getAction())) {
        throw new IllegalArgumentException(
            String.format(WRONG_ANALOG_ACTION, pinDao.getPinNumber(), pinDao.getAction()));
      }
      return;
    }

    throw new IllegalArgumentException(String.format(VALIDATION_FAILED, pinDao.getMode()));
  }
}
