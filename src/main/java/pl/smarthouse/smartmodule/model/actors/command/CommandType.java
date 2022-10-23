package pl.smarthouse.smartmodule.model.actors.command;

public interface CommandType {
  CommandType[] findAll();

  default String getName() {
    return getClass().getSimpleName();
  }
}
