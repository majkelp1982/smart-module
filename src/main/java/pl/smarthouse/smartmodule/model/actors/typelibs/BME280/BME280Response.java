package pl.smarthouse.smartmodule.model.actors.typelibs.BME280;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.response.Response;

@Getter
@Setter
@ToString(callSuper = true)
public class BME280Response extends Response {
  private double temperature;
  private int pressure;
  private int humidity;
}
