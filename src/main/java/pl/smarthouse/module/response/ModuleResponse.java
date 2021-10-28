package pl.smarthouse.module.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import pl.smarthouse.module.GPO.model.PinResponse;
import pl.smarthouse.module.sensors.model.SensorResponse;

import java.util.Set;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModuleResponse {

  @JsonProperty("type")
  private String type;

  @JsonProperty("version")
  private String version;

  @JsonProperty("pinResponseSet")
  private Set<PinResponse> pinResponseSet;

  @JsonProperty("sensorResponseSet")
  private Set<SensorResponse> sensorResponseSet;
}
