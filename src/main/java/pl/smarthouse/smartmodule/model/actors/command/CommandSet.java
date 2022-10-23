package pl.smarthouse.smartmodule.model.actors.command;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public abstract class CommandSet {
  private @NonNull CommandType commandType;
  private String value;
}
