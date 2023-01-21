package pl.smarthouse.smartmodule.model.actors.type.ds18b20;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.response.Response;

import java.util.Set;

@Getter
@Setter
@ToString(callSuper = true)
public class Ds18b20Response extends Response {
  private Set<Ds18b20Result> resultSet;
}
