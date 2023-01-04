package pl.smarthouse.smartmodule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.smarthouse.smartmodule.model.configuration.Configuration;
import pl.smarthouse.smartmodule.services.ManagerService;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ServiceController {
  private final ManagerService managerService;

  @GetMapping(value = "/actors")
  public Mono<Configuration> getActors() {
    return Mono.just(managerService.getConfiguration());
  }
}
