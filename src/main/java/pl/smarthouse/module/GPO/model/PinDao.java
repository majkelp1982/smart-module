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
    if (!action.equals(PinAction.NO_ACTION)) {
      if (mode.toString().contains(OUTPUT)
          && !List.of(PinAction.HIGH, PinAction.LOW).contains(action)) {
        throw new IllegalArgumentException(String.format(WRONG_ACTION, mode));
      }
      if (mode.toString().contains(INPUT) && !List.of(PinAction.READ).contains(action)) {
        throw new IllegalArgumentException(String.format(WRONG_ACTION, mode));
      }
      if (mode.equals(PinModes.ANALOG)
          && !List.of(PinAction.READ_ANALOG, PinAction.WRITE_ANALOG).contains(action)) {
        throw new IllegalArgumentException(String.format(WRONG_ACTION, mode));
      }
    }
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
      }
      return new PinDao(
          pinNumber, mode, standby, action, defaultLatchTime, 0, PinDigitalState.NO_STATE);
    }
  }
}
