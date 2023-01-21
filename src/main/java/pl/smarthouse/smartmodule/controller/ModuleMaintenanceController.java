package pl.smarthouse.smartmodule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.smarthouse.modulemanager.model.dto.SettingsDto;
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
}
