package pl.smarthouse.smartmodule.model.actors.type.ds18b20;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class Ds18b20ResponseValidator {
  public boolean isResultValid(final Ds18b20Result result) {
    if (result.getTemp() < -20 || result.getTemp() > 100) {
      log.warn(
          "DS180B20 sensor address: {}, Temperature invalid: {}",
          result.getAddress(),
          result.getTemp());
      return false;
    }
    return true;
  }

  public float applyCompensateTemperature(
      final float temperature, final Ds18b20CompFactor ds18b20CompFactor) {
    return (ds18b20CompFactor.getGradient() * temperature + ds18b20CompFactor.getIntercept());
  }
}
