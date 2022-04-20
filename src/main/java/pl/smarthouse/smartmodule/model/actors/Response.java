package pl.smarthouse.smartmodule.model.actors;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public abstract class Response {
  protected LocalDateTime responseUpdate;
}
