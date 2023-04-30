package pl.smarthouse.smartmodule.utils;

import java.util.HashMap;
import java.util.Map;
import pl.smarthouse.smartmodule.model.actors.command.CommandSet;
import pl.smarthouse.smartmodule.model.configuration.Configuration;
import pl.smarthouse.smartmodule.model.module.ModuleCommands;

public class CommandUtils {

  public static ModuleCommands getCommandBody(final Configuration configuration) {
    final Map<String, CommandSet> commandMap = new HashMap<>();

    configuration.getActorMap().stream()
        .filter(
            actor ->
                !"NO_ACTION".equalsIgnoreCase(actor.getCommandSet().getCommandType().toString()))
        .forEach(actor -> commandMap.put(actor.getName(), actor.getCommandSet()));

    return ModuleCommands.builder()
        .type(configuration.getType())
        .version(configuration.getVersion())
        .commandMap(commandMap)
        .build();
  }
}
