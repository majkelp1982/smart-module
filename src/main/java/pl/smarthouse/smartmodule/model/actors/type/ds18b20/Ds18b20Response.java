package pl.smarthouse.smartmodule.model.actors.type.ds18b20;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.response.Response;

@Getter
@Setter
@ToString(callSuper = true)
public class Ds18b20Response extends Response {
  private Set<Ds18b20Result> resultSet = new HashSet<>();

  public Ds18b20Result getSensorResult(final String address) {
    final Optional<Ds18b20Result> optionalResult =
        resultSet.stream().filter(result -> result.getAddress().equals(address)).findFirst();
    if (optionalResult.isPresent()) {
      return optionalResult.get();
    } else {
      return null;
    }
  }
}
