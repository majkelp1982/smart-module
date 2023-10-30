package pl.smarthouse.smartmodule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.smarthouse.sharedobjects.dto.SettingsDto;
import pl.smarthouse.smartmodule.model.configuration.Configuration;
import pl.smarthouse.smartmodule.services.ManagerService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/module")
@RequiredArgsConstructor
public class ModuleMaintenanceController {
  private final ManagerService managerService;

  @GetMapping(value = "/params")
  public Mono<SettingsDto> getModuleSettingsByMacAddress() {
    return managerService.getModuleSettingsByMacAddress(
        managerService.getConfiguration().getMacAddress());
  }

  @GetMapping(value = "/configuration")
  public Mono<Configuration> getConfiguration() {
    return Mono.just(managerService.getConfiguration());
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public Mono<String> exceptionHandler(final Exception exception) {
    return Mono.just(exception.getMessage());
  }
}
