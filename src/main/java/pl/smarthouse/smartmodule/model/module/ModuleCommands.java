package pl.smarthouse.smartmodule.model.module;

import java.util.Map;
import lombok.*;
import pl.smarthouse.smartmodule.model.actors.command.CommandSet;

@Getter
@EqualsAndHashCode
@ToString
@Builder
public class ModuleCommands {

  @NonNull private String type;
  @NonNull private String version;
  @NonNull private Map<String, CommandSet> commandMap;
}
