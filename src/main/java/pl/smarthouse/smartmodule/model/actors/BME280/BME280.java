package pl.smarthouse.smartmodule.model.actors.BME280;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import pl.smarthouse.smartmodule.model.actors.Actor;
import pl.smarthouse.smartmodule.model.actors.ActorType;
import pl.smarthouse.smartmodule.model.actors.Command;

@Setter
@Getter
public class BME280 extends Actor {
  private BME280Command command;
  private BME280Response response;

  public BME280(@NonNull final String name) {
    super(ActorType.BME280, name);
  }

  @Override
  public void setCommand(final Command command) {
    this.command = (BME280Command) command;
  }
}
