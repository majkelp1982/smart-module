package pl.smarthouse.smartmodule.services;

import java.net.ConnectException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import pl.smarthouse.sharedobjects.dto.SettingsDto;
import pl.smarthouse.smartmodule.exceptions.ValidatorException;
import pl.smarthouse.smartmodule.model.configuration.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerService {
  private static final String NO_IP_FOUND = "No IP found for mac address %s";
  private static final String FIRMWARE_DONT_MATCH =
      "Expected firmware: %s, don't match module firmware: %s";
  private static final String GETTING_IP_GOES_WRONG = "Getting IP address from module goes wrong";
  private static final String SUCCESSFUL_SET_BASE_IP = "Successful set base IP for module: {}";
  private static final long RETRY_MAX_ATTEMPT = 1L;
  private final Environment environment;
  private Configuration configuration;
  private final WebClient moduleManagerWebClient;

  private static int retryMs = 5000;
  private static final int MAX_RETRY_MS = 10 * 60 * 1000;

  public Mono<String> retrieveModuleIpAndCheckFirmwareVersion() {
    return getModuleSettingsByMacAddress(configuration.getMacAddress())
        .switchIfEmpty(
            Mono.defer(
                () ->
                    Mono.error(
                        new ConnectException(
                            String.format(NO_IP_FOUND, configuration.getMacAddress())))))
        .flatMap(this::checkFirmwareVersion)
        .flatMap(settingsDto -> updateServiceAddress(settingsDto.getModuleMacAddress()))
        .flatMap(this::setBaseUrl)
        .map(SettingsDto::toString)
        .doOnSuccess(settingsDto -> log.info(SUCCESSFUL_SET_BASE_IP, settingsDto))
        .doOnError(
            throwable -> {
              retryMs = retryMs * 2;
              if (retryMs > MAX_RETRY_MS) {
                retryMs = MAX_RETRY_MS;
              }
              log.error("Error occurred on retrieve module ip. Error: {}", throwable);
            })
        .retryWhen(
            Retry.fixedDelay(RETRY_MAX_ATTEMPT, Duration.ofMillis(retryMs))
                .onRetryExhaustedThrow(
                    (retryBackoffSpec, retrySignal) -> {
                      throw new RuntimeException(GETTING_IP_GOES_WRONG);
                    }));
  }

  private Mono<SettingsDto> checkFirmwareVersion(final SettingsDto settingsDto) {
    if (!configuration.getFirmware().equals(settingsDto.getModuleFirmwareVersion())) {
      throw new ValidatorException(
          String.format(
              FIRMWARE_DONT_MATCH,
              configuration.getFirmware(),
              settingsDto.getModuleFirmwareVersion()));
    }
    return Mono.just(settingsDto);
  }

  private Mono<SettingsDto> setBaseUrl(final SettingsDto settingsDto) {
    configuration.setBaseUrl(settingsDto.getModuleIpAddress());
    retryMs = 5000;
    return Mono.just(settingsDto);
  }

  public Mono<SettingsDto> getModuleSettingsByMacAddress(final String moduleMacAddress) {
    return moduleManagerWebClient
        .get()
        .uri("/settings?moduleMacAddress=" + moduleMacAddress)
        .exchangeToMono(this::processResponse);
  }

  private Mono<SettingsDto> updateServiceAddress(final String moduleMacAddress) {
    return Mono.justOrEmpty(environment.getProperty("server.port"))
        .switchIfEmpty(
            Mono.defer(
                () -> Mono.error(new UnknownHostException("Service port unable to retrieve"))))
        .flatMap(
            port -> {
              try {
                return Mono.just(InetAddress.getLocalHost().getHostAddress() + ":" + port);
              } catch (final UnknownHostException e) {
                return Mono.error(
                    new RuntimeException(
                        String.format(
                            "Error on getting host name for module mac address: %s",
                            moduleMacAddress)));
              }
            })
        .flatMap(
            serviceAddress ->
                moduleManagerWebClient
                    .put()
                    .uri(
                        "/updateServiceAddress?moduleMacAddress="
                            + moduleMacAddress
                            + "&serviceAddress="
                            + serviceAddress)
                    .exchangeToMono(this::processResponse));
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

  public Configuration getConfiguration() {
    return this.configuration;
  }

  public Mono<List<String>> getActorNames() {
    return Flux.fromStream(configuration.getActorMap().getActors().keySet().stream()).collectList();
  }
}
