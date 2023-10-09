package pl.smarthouse.smartmodule.utils.errorpredictions;

import java.util.concurrent.Callable;
import java.util.function.Predicate;
import lombok.experimental.UtilityClass;
import pl.smarthouse.sharedobjects.dao.ModuleDao;
import pl.smarthouse.sharedobjects.dto.error.ErrorPrediction;
import pl.smarthouse.smartmodule.model.actors.type.ds18b20.Ds18b20Response;
import pl.smarthouse.smartmodule.model.actors.type.ds18b20.Ds18b20ResponseValidator;
import pl.smarthouse.smartmonitoring.service.ErrorHandlingService;

@UtilityClass
public class Ds18b20ErrorPredictionsUtils {
  // Error messages
  private static final String DS18B20_TIMEOUT = "DS18B20 sensor: %s, update timeout";
  private static final String DS18B20_RESPONSE_ERROR =
      "DS18B20 sensor invalid response. Check error logs for actual values";

  public void setDs180b20SensorsErrorPredictions(
      final ErrorHandlingService errorHandlingService,
      final Callable<Ds18b20Response> sensorResponse) {
    sensorsReadTimeout(errorHandlingService, sensorResponse);
    sensorsInvalidResponse(errorHandlingService, sensorResponse);
  }

  private void sensorsInvalidResponse(
      final ErrorHandlingService errorHandlingService,
      final Callable<Ds18b20Response> sensorResponse) {
    final Predicate<ModuleDao> ds18b20invalidResponse =
        ignore -> {
          try {
            return sensorResponse.call().getResultSet().stream()
                .anyMatch(ds18b20Result -> !Ds18b20ResponseValidator.isResultValid(ds18b20Result));
          } catch (final Exception e) {
            throw new RuntimeException(e);
          }
        };
    errorHandlingService.add(
        new ErrorPrediction(
            DS18B20_RESPONSE_ERROR,
            0,
            ds18b20invalidResponse,
            result -> {
              Ds18b20Response ds18b20Response = null;
              try {
                ds18b20Response = sensorResponse.call();
              } catch (final Exception e) {
                throw new RuntimeException(e);
              }
              ds18b20Response
                  .getResultSet()
                  .forEach(
                      ds18b20Result ->
                          ds18b20Result.setError(
                              !Ds18b20ResponseValidator.isResultValid(ds18b20Result)));
            }));
  }

  private void sensorsReadTimeout(
      final ErrorHandlingService errorHandlingService,
      final Callable<Ds18b20Response> sensorResponse) {
    final Predicate<ModuleDao> ds18b20Timeout =
        ignore -> {
          try {
            return sensorResponse.call().getResultSet().stream()
                .anyMatch(
                    ds18b20Result ->
                        TimeoutUtils.updateTimeout.test(ds18b20Result.getLastUpdate()));
          } catch (final Exception e) {
            throw new RuntimeException(e);
          }
        };
    errorHandlingService.add(
        new ErrorPrediction(
            DS18B20_TIMEOUT,
            0,
            ds18b20Timeout,
            result -> {
              Ds18b20Response ds18b20Response = null;
              try {
                ds18b20Response = sensorResponse.call();
              } catch (final Exception e) {
                throw new RuntimeException(e);
              }
              ds18b20Response
                  .getResultSet()
                  .forEach(
                      ds18b20Result ->
                          ds18b20Result.setError(
                              TimeoutUtils.updateTimeout.test(ds18b20Result.getLastUpdate())));
            }));
  }
}
