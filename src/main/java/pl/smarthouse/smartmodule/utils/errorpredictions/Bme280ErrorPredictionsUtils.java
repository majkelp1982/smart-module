package pl.smarthouse.smartmodule.utils.errorpredictions;

import java.util.concurrent.Callable;
import java.util.function.Predicate;
import lombok.experimental.UtilityClass;
import pl.smarthouse.sharedobjects.dao.ModuleDao;
import pl.smarthouse.sharedobjects.dto.error.ErrorPrediction;
import pl.smarthouse.smartmodule.model.actors.type.bme280.Bme280Response;
import pl.smarthouse.smartmodule.model.actors.type.bme280.Bme280ResponseValidator;
import pl.smarthouse.smartmonitoring.service.ErrorHandlingService;

@UtilityClass
public class Bme280ErrorPredictionsUtils {
  // Error messages
  private static final String BME280_TIMEOUT = "BME280 sensor: %s, update timeout";
  private static final String BME280_RESPONSE_ERROR =
      "BME280 sensor: %s, invalid response. Check error logs for actual values";

  public void setBme280SensorErrorPredictions(
      final ErrorHandlingService errorHandlingService,
      final String sensorName,
      final Callable<Bme280Response> sensorResponse) {
    sensorReadTimeout(errorHandlingService, sensorName, sensorResponse);
    sensorInvalidResponse(errorHandlingService, sensorName, sensorResponse);
  }

  private void sensorInvalidResponse(
      final ErrorHandlingService errorHandlingService,
      final String sensorName,
      final Callable<Bme280Response> sensorResponse) {
    final Predicate<ModuleDao> bme280invalidResponse =
        ignore -> {
          try {
            return !Bme280ResponseValidator.isResponseValid(sensorName, sensorResponse.call());
          } catch (final Exception e) {
            throw new RuntimeException(e);
          }
        };

    errorHandlingService.add(
        new ErrorPrediction(
            String.format(BME280_RESPONSE_ERROR, sensorName),
            0,
            bme280invalidResponse,
            result -> {
              try {
                sensorResponse.call().setError(result);
              } catch (final Exception e) {
                throw new RuntimeException(e);
              }
            }));
  }

  private void sensorReadTimeout(
      final ErrorHandlingService errorHandlingService,
      final String sensorName,
      final Callable<Bme280Response> sensorResponse) {
    final Predicate<ModuleDao> bme280Timeout =
        ignore -> {
          try {
            return TimeoutUtils.updateTimeout.test(sensorResponse.call().getResponseUpdate());
          } catch (final Exception e) {
            throw new RuntimeException(e);
          }
        };

    errorHandlingService.add(
        new ErrorPrediction(
            String.format(BME280_TIMEOUT, sensorName),
            0,
            bme280Timeout,
            result -> {
              try {
                sensorResponse.call().setError(result);
              } catch (final Exception e) {
                throw new RuntimeException(e);
              }
            }));
  }
}
