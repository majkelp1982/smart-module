package pl.smarthouse.smartmodule.model.actors.type.pwm;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.actor.Actor;
import pl.smarthouse.smartmodule.model.actors.command.CommandSet;
import pl.smarthouse.smartmodule.model.enums.ActorType;

import java.time.LocalDateTime;
import java.util.Map;

@Setter
@Getter
@ToString(callSuper = true)
public class Pwm extends Actor {
  private PwmCommandSet commandSet;
  private PwmResponse response;
  private int channel;
  private int frequency;
  private int resolution;
  private int pin;
  private int defaultDutyCycle;
  private boolean defaultEnabled;

  public Pwm(
      @NonNull final String name,
      final int channel,
      final int frequency,
      final int resolution,
      final int pin,
      final int defaultDutyCycle,
      final boolean defaultEnabled) {
    super(ActorType.PWM, name);
    this.channel = channel;
    this.frequency = frequency;
    this.resolution = resolution;
    this.pin = pin;
    this.defaultDutyCycle = defaultDutyCycle;
    this.defaultEnabled = defaultEnabled;
    setCommandSet(new PwmCommandSet(PwmCommandType.NO_ACTION));
  }

  @Override
  public CommandSet getCommandSet() {
    return commandSet;
  }

  @Override
  public void setResponse(final Map response) {
    final ObjectMapper objectMapper = new ObjectMapper();
    this.response = objectMapper.convertValue(response, PwmResponse.class);
    this.response.setResponseUpdate(LocalDateTime.now());
  }
}
