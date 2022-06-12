package pl.smarthouse.smartmodule.model.module;

import lombok.*;
import pl.smarthouse.smartmodule.model.types.ModuleType;

import java.util.Map;

@Getter
@EqualsAndHashCode
@ToString
@Builder
public class ModuleCommands {

  @NonNull private ModuleType type;
  @NonNull private String version;
  @NonNull private Map<String, String> commandMap;
}
