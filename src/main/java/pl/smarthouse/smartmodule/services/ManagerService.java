package pl.smarthouse.smartmodule.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import pl.smarthouse.loghandler.model.ErrorDto;
import pl.smarthouse.loghandler.service.LogService;
import pl.smarthouse.loghandler.utils.LogUtils;
import pl.smarthouse.modulemanager.configuration.ModuleManagerConfiguration;
import pl.smarthouse.modulemanager.model.dto.SettingsDto;
import pl.smarthouse.smartmodule.exceptions.ValidatorException;
import pl.smarthouse.smartmodule.model.configuration.Configuration;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.net.ConnectException;
import java.time.Duration;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerService {
  private static final String NO_IP_FOUND = "No IP found for mac address %s";
  private static final String FIRMWARE_DONT_MATCH =
      "Service firmware declaration: %s, don't match module firmware: %s";
  private static final String GETTING_IP_GOES_WRONG = "Getting IP address from module goes wrong";
  private static final String SUCCESSFUL_SET_BASE_IP = "Successful set base IP for module: {}";
  private static final long RETRY_MAX_ATTEMPT = 1L;

  private final ModuleManagerConfiguration moduleManagerConfiguration =
      new ModuleManagerConfiguration();
  private Configuration configuration;
  private final LogService logService;

  private static int retryMs = 5000;
  private static final int MAX_RETRY_MS = 10 * 60 * 1000;

  public void retrieveModuleIpAndCheckFirmwareVersion() {
    if (!Objects.isNull(configuration.getBaseUrl()) && !configuration.getBaseUrl().isEmpty()) {
      return;
    }

    getModuleSettingsByMacAddress(configuration.getMacAddress())
        .switchIfEmpty(
            Mono.defer(
                () ->
                    Mono.error(
                        new ConnectException(
                            String.format(NO_IP_FOUND, configuration.getMacAddress())))))
        .flatMap(this::checkFirmwareVersion)
        .flatMap(this::setBaseUrl)
        .doOnSuccess(settingsDto -> log.info(SUCCESSFUL_SET_BASE_IP, settingsDto))
        .doOnError(
            throwable -> {
              retryMs = retryMs * 2;
              if (retryMs > MAX_RETRY_MS) {
                retryMs = MAX_RETRY_MS;
              }
              final ErrorDto errorDto =
                  LogUtils.error(configuration.getType(), throwable.getMessage());
              logService.error(errorDto);
            })
        .retryWhen(
            Retry.fixedDelay(RETRY_MAX_ATTEMPT, Duration.ofMillis(retryMs))
                .onRetryExhaustedThrow(
                    (retryBackoffSpec, retrySignal) -> {
                      throw new RuntimeException(GETTING_IP_GOES_WRONG);
                    }))
        .block();
  }

  private Mono<SettingsDto> checkFirmwareVersion(final SettingsDto settingsDto) {
    if (!configuration.getFirmware().equals(settingsDto.getFirmware())) {
      throw new ValidatorException(
          String.format(
              FIRMWARE_DONT_MATCH, configuration.getFirmware(), settingsDto.getFirmware()));
    }
    return Mono.just(settingsDto);
  }

  private Mono<SettingsDto> setBaseUrl(final SettingsDto settingsDto) {
    configuration.setBaseUrl(settingsDto.getIpAddress());
    retryMs = 5000;
    return Mono.just(settingsDto);
  }

  public Mono<SettingsDto> getModuleSettingsByMacAddress(final String macAddress) {
    return moduleManagerConfiguration
        .webClient()
        .get()
        .uri("/settings?macAddress=" + macAddress)
        .exchangeToMono(this::processResponse);
  }

  private Mono<SettingsDto> processResponse(final ClientResponse clientResponse) {
    if (clientResponse.statusCode().is2xxSuccessful()) {
      return clientResponse.bodyToMono(SettingsDto.class);
    } else {
      return Mono.error(
          new RuntimeException(
              String.format(
                  "Error on communication with manager service. Status code: %s, Reason: %s",
                  clientResponse.statusCode(),
                  clientResponse.body((inputMessage, context) -> inputMessage.getBody()))));
    }
  }

  public void setConfiguration(final Configuration configuration) {
    this.configuration = configuration;
  }
}
