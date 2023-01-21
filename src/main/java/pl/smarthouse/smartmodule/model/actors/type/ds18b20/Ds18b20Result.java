package pl.smarthouse.smartmodule.model.actors.type.ds18b20;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Ds18b20Result {
  private String address;
  private float temp;
}
