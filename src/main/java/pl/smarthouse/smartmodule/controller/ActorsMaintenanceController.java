package pl.smarthouse.smartmodule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.smarthouse.smartmodule.model.actors.actor.Actor;
import pl.smarthouse.smartmodule.model.configuration.Configuration;
import pl.smarthouse.smartmodule.services.MaintenanceService;
import pl.smarthouse.smartmodule.services.ManagerService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/actors")
@RequiredArgsConstructor
public class ActorsMaintenanceController {
  private final ManagerService managerService;
  private final MaintenanceService maintenanceService;

  @GetMapping
  public Mono<Configuration> getActors() {
    return Mono.just(managerService.getConfiguration());
  }

  @GetMapping(value = "/actorByName")
  public Mono<Actor> getActorByName(@RequestParam final String actorName) {
    return Mono.just(managerService.getConfiguration().getActorMap().getActor(actorName));
  }

  @GetMapping(value = "/names")
  public Mono<List<String>> getActorsNames() {
    return managerService.getActorNames();
  }

  @PostMapping(value = "/{actorName}/command")
  public Mono<Map> setActorCommand(
      @PathVariable final String actorName,
      @RequestParam final String command,
      @RequestParam(required = false) final String value) {
    if ("ALL".equalsIgnoreCase(actorName)) {
      Flux.fromStream(managerService.getConfiguration().getActorMap().stream())
          .flatMap(
              actor ->
                  maintenanceService.setActorCommand(
                      actor.getName(),
                      command.toUpperCase(),
                      (value != null) ? value.toUpperCase() : null))
          .then(Mono.empty())
          .subscribe();
    }
    return maintenanceService.setActorCommand(
        actorName, command.toUpperCase(), (value != null) ? value.toUpperCase() : null);
  }
}
