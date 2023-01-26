package pl.smarthouse.smartmodule.model.actors.type.ds18b20;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Ds18b20CompFactor {
  private float gradient;
  private final float intercept;
}
