package pl.smarthouse.smartmodule.utils.errorpredictions;

import java.util.concurrent.Callable;
import java.util.function.Predicate;
import lombok.experimental.UtilityClass;
import pl.smarthouse.sharedobjects.dao.ModuleDao;
import pl.smarthouse.sharedobjects.dto.error.ErrorPrediction;
import pl.smarthouse.smartmodule.model.actors.type.pin.PinResponse;
import pl.smarthouse.smartmonitoring.service.ErrorHandlingService;

@UtilityClass
public class PinErrorPredictionsUtils {
  // Error messages
  private static final String PIN_TIMEOUT = "Pin: %s, update timeout";
  private static final String PIN_ERROR =
      "Pin: %s, invalid response. Check error logs for actual values";

  public void setPinErrorPredictions(
      final ErrorHandlingService errorHandlingService,
      final String sensorName,
      final Callable<Boolean> isValid,
      final Callable<PinResponse> sensorResponse) {
    sensorReadTimeout(errorHandlingService, sensorName, sensorResponse);
    sensorInvalidResponse(errorHandlingService, sensorName, isValid, sensorResponse);
  }

  private void sensorInvalidResponse(
      final ErrorHandlingService errorHandlingService,
      final String sensorName,
      final Callable<Boolean> isValid,
      final Callable<PinResponse> sensorResponse) {
    final Predicate<ModuleDao> pinInvalidResponse =
        ignore -> {
          try {
            return !isValid.call();
          } catch (final Exception e) {
            throw new RuntimeException(e);
          }
        };

    errorHandlingService.add(
        new ErrorPrediction(
            String.format(PIN_ERROR, sensorName),
            0,
            pinInvalidResponse,
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
      final Callable<PinResponse> sensorResponse) {
    final Predicate<ModuleDao> pinValueTimeout =
        ignore -> {
          try {
            return TimeoutUtils.updateTimeout.test(sensorResponse.call().getResponseUpdate());
          } catch (final Exception e) {
            throw new RuntimeException(e);
          }
        };

    errorHandlingService.add(
        new ErrorPrediction(
            String.format(PIN_TIMEOUT, sensorName),
            0,
            pinValueTimeout,
            result -> {
              try {
                sensorResponse.call().setError(result);
              } catch (final Exception e) {
                throw new RuntimeException(e);
              }
            }));
  }
}
