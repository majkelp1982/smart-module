package pl.smarthouse.smartmodule.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.smarthouse.smartmodule.exceptions.InvalidCommandException;
import pl.smarthouse.smartmodule.model.actors.actor.Actor;
import pl.smarthouse.smartmodule.model.actors.command.CommandSet;
import pl.smarthouse.smartmodule.model.actors.type.bme280.Bme280CommandType;
import pl.smarthouse.smartmodule.model.actors.type.pca9685.Pca9685CommandType;
import pl.smarthouse.smartmodule.model.actors.type.pin.PinCommandType;
import pl.smarthouse.smartmodule.model.actors.type.pwm.PwmCommandType;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MaintenanceService {
  private final ManagerService managerService;
  private final ModuleService moduleService;

  public Mono<Map> exchangeWithModule() {
    return moduleService.exchange();
  }

  public Mono<Actor> setActorCommand(
      @NonNull final String actorName, @NonNull final String command, final String value) {
    return Mono.justOrEmpty(managerService.getConfiguration().getActorMap().getActor(actorName))
        .switchIfEmpty(
            Mono.defer(
                () ->
                    Mono.error(
                        new IllegalArgumentException(
                            String.format("Actor name: %s not found", actorName)))))
        .flatMap(actor -> generateCommand(actor, command, value));
  }

  private Mono<Actor> generateCommand(
      @NonNull final Actor actor, @NonNull final String command, final String value) {
    final CommandSet commandSet = actor.getCommandSet();
    switch (actor.getType()) {
      case BME280:
        commandSet.setCommandType(Bme280CommandType.valueOf(command));
        break;
      case PWM:
        commandSet.setCommandType(PwmCommandType.valueOf(command));
        break;
      case PIN:
        commandSet.setCommandType(PinCommandType.valueOf(command));
        break;
      case PCA9685:
        commandSet.setCommandType(Pca9685CommandType.valueOf(command));
        break;
      default:
        throw new InvalidCommandException("Unable to generate command. Actor type not recognized");
    }
    commandSet.setValue(value);
    return Mono.just(actor);
  }
}
