package pl.smarthouse.smartmodule.model.actors.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public abstract class Response {
  protected LocalDateTime responseUpdate;
  private boolean error;
}
