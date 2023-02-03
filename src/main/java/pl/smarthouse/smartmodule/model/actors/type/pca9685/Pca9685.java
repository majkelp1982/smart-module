package pl.smarthouse.smartmodule.model.actors.type.pca9685;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Pca9685 extends Actor {
  @JsonIgnore private Pca9685CommandSet commandSet;
  @JsonIgnore private Pca9685Response response;
  int servoFrequencyHz;

  public Pca9685(@NonNull final String name, @NonNull final int servoFrequencyHz) {
    super(ActorType.PCA9685, name);
    this.servoFrequencyHz = servoFrequencyHz;
    setCommandSet(new Pca9685CommandSet(Pca9685CommandType.NO_ACTION));
  }

  @Override
  public CommandSet getCommandSet() {
    return commandSet;
  }

  @Override
  public void setResponse(final Map response) {
    final ObjectMapper objectMapper = new ObjectMapper();
    this.response = objectMapper.convertValue(response, Pca9685Response.class);
    this.response.setResponseUpdate(LocalDateTime.now());
  }
}
