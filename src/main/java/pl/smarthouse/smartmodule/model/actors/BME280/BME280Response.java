package pl.smarthouse.smartmodule.model.actors.BME280;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BME280Response {
  private double temperature;
  private int pressure;
  private int humidity;
}
