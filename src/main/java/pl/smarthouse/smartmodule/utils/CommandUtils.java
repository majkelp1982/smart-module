package pl.smarthouse.smartmodule.utils;

import pl.smarthouse.smartmodule.model.actors.Command;
import pl.smarthouse.smartmodule.model.configuration.Configuration;
import pl.smarthouse.smartmodule.model.module.ModuleCommands;

import java.util.HashMap;
import java.util.Map;

public class CommandUtils {

  public static ModuleCommands getCommandBody(final Configuration configuration) {
    final Map<String, Command> commandMap = new HashMap<>();

    configuration.getActorMap().stream()
        .filter(actor -> !"NO_ACTION".equalsIgnoreCase(actor.getCommand().toString()))
        .forEach(actor -> commandMap.put(actor.getName(), actor.getCommand()));

    return ModuleCommands.builder()
        .type(configuration.getType())
        .version(configuration.getVersion())
        .commandMap(commandMap)
        .build();
  }
}
