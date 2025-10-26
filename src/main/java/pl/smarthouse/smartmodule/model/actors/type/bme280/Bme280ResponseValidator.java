package pl.smarthouse.smartmodule.model.actors.type.bme280;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class Bme280ResponseValidator {
  public boolean isResponseValid(final String name, final Bme280Response response) {
    if (response.getTemperature() < -35 || response.getTemperature() > 120) {
      log.warn("BME280: {}, Temperature invalid: {}", name, response.getTemperature());
      return false;
    }

    if (response.getHumidity() <= -10 || response.getHumidity() > 100) {
      log.warn("BME280: {}, Humidity invalid: {}", name, response.getHumidity());
      return false;
    }
    if (response.getPressure() < 900 || response.getPressure() > 1100) {
      log.warn("BME280: {}, Pressure invalid: {}", name, response.getPressure());
      return false;
    }

    return true;
  }
}
