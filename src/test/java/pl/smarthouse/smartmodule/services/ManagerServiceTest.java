package pl.smarthouse.smartmodule.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.smarthouse.sharedobjects.dto.SettingsDto;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class ManagerServiceTest {
  private static final String MAC_ADDRESS = "XX:XX:XX:XX:XX:XX";
  @Autowired ManagerService managerService;

  // getModuleSettingsByMacAddress
  @Test
  void givenMacAddressWhenExecuteThenDtoIsReturned() {
    final Mono<SettingsDto> getModuleSettingsByMacAccress =
        managerService.getModuleSettingsByMacAddress(MAC_ADDRESS);

    StepVerifier.create(getModuleSettingsByMacAccress)
        .expectNextMatches(settingsDto -> settingsDto.getModuleIpAddress().equals("localhost:8081"))
        .verifyComplete();
  }
}
