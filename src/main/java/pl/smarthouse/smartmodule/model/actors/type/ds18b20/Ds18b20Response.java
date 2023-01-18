package pl.smarthouse.smartmodule.model.actors.type.ds18b20;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.smarthouse.smartmodule.model.actors.response.Response;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class Ds18b20Response extends Response {
  private List<Result> resultList;

  class Result {
    private String address;
    private float temp;
  }
}
