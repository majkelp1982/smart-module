package pl.smarthouse.smartmodule.model.actors.type.bme280;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class Bme280ResponseValidator {
  public boolean isResponseValid(final String name, final Bme280Response response) {
    boolean valid = true;

    if (response.getTemperature() < -10 || response.getTemperature() > 100) {
      log.warn("BME280: {}, Temperature invalid: {}", name, response.getTemperature());
      valid = false;
    }

    if (response.getHumidity() <= 0 || response.getHumidity() > 100) {
      log.warn("BME280: {}, Humidity invalid: {}", name, response.getHumidity());
      valid = false;
    }
    if (response.getPressure() < 900 || response.getPressure() > 1100) {
      log.warn("BME280: {}, Pressure invalid: {}", name, response.getPressure());
      valid = false;
    }

    return valid;
  }
}
