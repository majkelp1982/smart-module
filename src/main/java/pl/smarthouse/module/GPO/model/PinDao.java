package pl.smarthouse.module.GPO.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import pl.smarthouse.module.GPO.enums.PinAction;
import pl.smarthouse.module.GPO.enums.PinDigitalState;
import pl.smarthouse.module.GPO.enums.PinModes;

import java.util.List;

@Getter
@Builder(builderClassName = "Builder", buildMethodName = "build")
public class PinDao {
  private static final String WRONG_INPUT_ACTION = "Pin number %s is input. Can't run %s action";
  private static final String WRONG_DIGITAL_ACTION = "Pin number %s is output. Can't run %s action";
  private static final String WRONG_ANALOG_ACTION = "Pin number %s is analog. Can't run %s action";
  private static final String VALIDATION_FAILED = "Validation failed. Mode %s not recognized";

  private static final String WRONG_ACTION = "Wrong action. Pin modes is %s";
  private static final String INPUT = "INPUT";
  private static final String OUTPUT = "OUTPUT";

  @NonNull private int pinNumber;
  @NonNull private PinModes mode;
  private PinAction standby;
  private PinAction action;
  private int defaultLatchTime;

  // States
  private int analogState;
  private PinDigitalState digitalState;

  public void setAction(final PinAction action) {
    validation(action);
    this.action = action;
  }

  public void setAnalogState(final int analogState) {
    if (!mode.equals(PinModes.ANALOG)) {
      throw new IllegalArgumentException(String.format(WRONG_ACTION, mode));
    }
    this.analogState = analogState;
  }

  public void setDigitalState(final PinDigitalState digitalState) {
    if (!mode.toString().contains(INPUT)) {
      throw new IllegalArgumentException(String.format(WRONG_ACTION, mode));
    }
    this.digitalState = digitalState;
  }

  public static class Builder {
    public PinDao build() {
      if (pinNumber <= 0) {
        throw new IllegalArgumentException("Invalid pin number: " + pinNumber);
      }

      if (mode.toString().contains(INPUT)) {
        if (standby != null || defaultLatchTime != 0) {
          throw new IllegalArgumentException(
              "Invalid pin parameters. For INPUT can't define standby, state and latch time");
        }
      }
      // output
      if (mode.toString().contains(OUTPUT)) {
        if (defaultLatchTime <= 0) {
          throw new IllegalArgumentException("Invalid pin latch time. Need to be bigger than 0");
        }
        if (standby == null || !List.of(PinAction.LOW, PinAction.HIGH).contains(standby)) {
          throw new IllegalArgumentException("Invalid pin standby. Need to be HIGH or LOW");
        }
      }
      return new PinDao(
          pinNumber, mode, standby, action, defaultLatchTime, 0, PinDigitalState.NO_STATE);
    }
  }

  private void validation(final PinAction action) {
    if (action == PinAction.NO_ACTION) {
      return;
    }

    // input
    // fixme inline comment. Check PinModes enum
    if (List.of(PinModes.INPUT, /*PinModes.INPUT_PULLUP,*/ PinModes.INPUT_PULLDOWN)
        .contains(mode)) {
      if (!PinAction.READ.equals(action)) {
        throw new IllegalArgumentException(String.format(WRONG_INPUT_ACTION, pinNumber, action));
      }
    }

    // output
    if (List.of(PinModes.OUTPUT, PinModes.OUTPUT_OPEN_DRAIN).contains(mode)) {
      if (!List.of(PinAction.LOW, PinAction.HIGH).contains(action)) {
        throw new IllegalArgumentException(String.format(WRONG_DIGITAL_ACTION, pinNumber, action));
      }
    }

    // analog
    if (List.of(PinModes.ANALOG).contains(mode)) {
      if (!List.of(PinAction.READ_ANALOG, PinAction.WRITE_ANALOG).contains(action)) {
        throw new IllegalArgumentException(String.format(WRONG_ANALOG_ACTION, pinNumber, action));
      }
    }
  }
}
