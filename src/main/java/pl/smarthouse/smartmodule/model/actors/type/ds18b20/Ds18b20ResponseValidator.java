package pl.smarthouse.smartmodule.model.actors.type.ds18b20;

import java.time.LocalDateTime;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class Ds18b20ResponseValidator {
  public void isResultValid(
      final String actorName,
      final Ds18b20Result result,
      final Ds18b20CompFactor ds18b20CompFactor) {
    if (result.getTemp() < -20 || result.getTemp() > 100) {
      log.warn(
          "DS180B20: {}, address: {}, Temperature invalid: {}",
          actorName,
          result.getAddress(),
          result.getTemp());
      result.setError(true);
    } else {
      result.setError(false);
      if (ds18b20CompFactor != null) {
        result.setTemp(compensateTemperature(result.getTemp(), ds18b20CompFactor));
      }
      result.setLastUpdate(LocalDateTime.now());
    }
  }

  public float compensateTemperature(
      final float temperature, final Ds18b20CompFactor ds18b20CompFactor) {
    return (ds18b20CompFactor.getGradient() * temperature + ds18b20CompFactor.getIntercept());
  }
}
