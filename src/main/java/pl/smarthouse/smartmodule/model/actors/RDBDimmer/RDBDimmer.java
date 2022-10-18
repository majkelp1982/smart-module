package pl.smarthouse.smartmodule.model.actors.RDBDimmer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import pl.smarthouse.smartmodule.model.actors.Actor;

import java.time.LocalDateTime;
import java.util.Map;

@Setter
@Getter
public class RDBDimmer extends Actor {
  private RDBDimmerCommand command;
  private RDBDimmerResponse response;
  private int outputPin;
  private int crossZeroPin;

  public RDBDimmer(@NonNull final String name, final int outputPin, final int crossZeroPin) {
    super(name);
    this.outputPin = outputPin;
    this.crossZeroPin = crossZeroPin;
    setCommand(RDBDimmerCommand.NO_ACTION);
  }

  @Override
  public void setResponse(final Map response) {
    final ObjectMapper objectMapper = new ObjectMapper();
    this.response = objectMapper.convertValue(response, RDBDimmerResponse.class);
    this.response.setResponseUpdate(LocalDateTime.now());
  }
}
