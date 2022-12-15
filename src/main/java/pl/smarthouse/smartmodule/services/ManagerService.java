package pl.smarthouse.smartmodule.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.smarthouse.loghandler.model.ErrorDto;
import pl.smarthouse.loghandler.service.LogService;
import pl.smarthouse.loghandler.utils.LogUtils;
import pl.smarthouse.modulemanager.configuration.ModuleManagerConfiguration;
import pl.smarthouse.smartmodule.model.configuration.Configuration;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.net.ConnectException;
import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerService {
  private static final String NO_IP_FOUND = "No IP found for mac address %s";
  private static final String GETTING_IP_GOES_WRONG = "Getting IP address from module goes wrong";
  private static final long RETRY_MAX_ATTEMPT = 1L;

  private final ModuleManagerConfiguration moduleManagerConfiguration =
      new ModuleManagerConfiguration();
  private Configuration configuration;
  private final LogService logService;

  private static int retryMs = 5000;
  private static final int MAX_RETRY_MS = 10 * 60 * 1000;

  public void retrieveModuleIP() {
    Mono.justOrEmpty(configuration.getBaseUrl())
        .switchIfEmpty(getDBModuleIpAddress(configuration.getMacAddress()))
        .switchIfEmpty(
            Mono.error(
                new ConnectException(String.format(NO_IP_FOUND, configuration.getMacAddress()))))
        .doOnSuccess(
            (ip) -> {
              configuration.setBaseUrl(ip);
              retryMs = 5000;
            })
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

  private Mono<String> getDBModuleIpAddress(final String macAddress) {
    return moduleManagerConfiguration
        .webClient()
        .get()
        .uri("/ip?macAddress=" + macAddress)
        .retrieve()
        .bodyToMono(String.class);
  }

  public void setConfiguration(final Configuration configuration) {
    this.configuration = configuration;
  }
}
