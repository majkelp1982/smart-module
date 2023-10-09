package pl.smarthouse.smartmodule.utils.errorpredictions;

import java.util.concurrent.Callable;
import java.util.function.Predicate;
import lombok.experimental.UtilityClass;
import pl.smarthouse.sharedobjects.dao.ModuleDao;
import pl.smarthouse.sharedobjects.dto.error.ErrorPrediction;
import pl.smarthouse.smartmodule.model.actors.type.ds18b20.Ds18b20ResponseValidator;
import pl.smarthouse.smartmodule.model.actors.type.ds18b20.Ds18b20Result;
import pl.smarthouse.smartmonitoring.service.ErrorHandlingService;

@UtilityClass
public class Ds18b20ErrorPredictionsUtils {
  // Error messages
  private static final String DS18B20_TIMEOUT = "DS18B20 sensor: %s, update timeout";
  private static final String DS18B20_RESPONSE_ERROR =
      "DS18B20 sensor: %s, invalid response. Check error logs for actual values";

  public void setDs180b20SensorsErrorPredictions(
      final ErrorHandlingService errorHandlingService,
      final String sensorName,
      final Callable<Ds18b20Result> sensorResult) {
    sensorsReadTimeout(errorHandlingService, sensorName, sensorResult);
    sensorsInvalidResponse(errorHandlingService, sensorName, sensorResult);
  }

  private void sensorsInvalidResponse(
      final ErrorHandlingService errorHandlingService,
      final String sensorName,
      final Callable<Ds18b20Result> sensorResult) {
    final Predicate<ModuleDao> ds18b20invalidResponse =
        ignore -> {
          try {
            return !Ds18b20ResponseValidator.isResultValid(sensorResult.call());
          } catch (final Exception e) {
            throw new RuntimeException(e);
          }
        };

    errorHandlingService.add(
        new ErrorPrediction(
            String.format(DS18B20_RESPONSE_ERROR, sensorName),
            0,
            ds18b20invalidResponse,
            result -> {
              try {
                sensorResult.call().setError(result);
              } catch (final Exception e) {
                throw new RuntimeException(e);
              }
            }));
  }

  private void sensorsReadTimeout(
      final ErrorHandlingService errorHandlingService,
      final String sensorName,
      final Callable<Ds18b20Result> sensorResult) {
    final Predicate<ModuleDao> ds18b20Timeout =
        ignore -> {
          if (sensorResult == null) {
            return true;
          }
          try {
            return TimeoutUtils.updateTimeout.test(sensorResult.call().getLastUpdate());
          } catch (final Exception e) {
            throw new RuntimeException(e);
          }
        };

    errorHandlingService.add(
        new ErrorPrediction(
            String.format(DS18B20_TIMEOUT, sensorName),
            0,
            ds18b20Timeout,
            result -> {
              try {
                sensorResult.call().setError(result);
              } catch (final Exception e) {
                throw new RuntimeException(e);
              }
            }));
  }
}
