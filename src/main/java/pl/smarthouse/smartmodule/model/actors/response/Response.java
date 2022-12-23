package pl.smarthouse.smartmodule.model.actors.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public abstract class Response {
  protected LocalDateTime responseUpdate;
}
