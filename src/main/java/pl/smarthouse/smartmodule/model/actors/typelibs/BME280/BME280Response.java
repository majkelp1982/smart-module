package pl.smarthouse.smartmodule.model.actors.typelibs.BME280;

import lombok.Getter;
import lombok.Setter;
import pl.smarthouse.smartmodule.model.actors.response.Response;

@Getter
@Setter
public class BME280Response extends Response {
  private double temperature;
  private int pressure;
  private int humidity;
}
