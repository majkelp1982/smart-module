package pl.smarthouse.smartmodule.model.actors.command;

import lombok.NonNull;
import pl.smarthouse.smartmodule.exceptions.InvalidCommandException;
import pl.smarthouse.smartmodule.exceptions.ValidatorException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class CommandValidator {
  private final String VALIDATOR_NOT_DEFINED = "Validator for command type: %s not defined";
  private final String VALIDATOR_INCONSISTENT =
      "Validator for type: %s is inconsistent. Types are not equal";
  private final String VALIDATOR_NO_ACTION_READ =
      "Command type: %s, do not contain NO_ACTION or READ actions";
  public final String COMMAND_AND_VALUE_DO_NOT_MATCH =
      "Command: %s and Value: %s not match. Allowed values for this command: %s";
  public final String INVALID_VALUE =
      "According to validator value should be a number, but it is not parsable: %s";

  protected final Map<CommandType, List> commandValidationMap = new HashMap<>();

  private final List<String> specialActions = List.of("NO_ACTION", "READ");

  protected abstract void prepareValidationMap();

  public void validate(@NonNull final CommandType commandType, final String value) {
    checkValidatorDefinitions(commandType);

    if (!specialActions.contains(commandType.toString())) {
      valueCheck(commandType, commandValidationMap.get(commandType), value);
    }
  }

  private void valueCheck(final CommandType commandType, final List rules, final String value) {
    if (rules.get(0) instanceof String) {
      if (!rules.contains(value)) {
        throw new InvalidCommandException(
            String.format(COMMAND_AND_VALUE_DO_NOT_MATCH, commandType, value, rules));
      }
    } else {
      valueCheck(commandType, value, (int) rules.get(0), (int) rules.get(1));
    }
  }

  private void checkValidatorDefinitions(@NonNull final CommandType commandType) {
    final List<String> commandTypeStr =
        Arrays.stream(commandType.findAll()).map(Object::toString).collect(Collectors.toList());

    specialActions.forEach(
        specActions -> {
          if (!commandTypeStr.contains(specActions)) {
            throw new ValidatorException(
                String.format(VALIDATOR_NO_ACTION_READ, commandType.getName()));
          }
        });
    List.of(commandType.findAll())
        .forEach(
            type -> {
              if ((!specialActions.contains(type.toString().toUpperCase()))
                  && (commandValidationMap.get(type) == null)) {
                throw new ValidatorException(String.format(VALIDATOR_NOT_DEFINED, type));
              }
            });

    commandValidationMap.forEach(
        (key, rule) -> {
          final String ruleClassName = rule.get(0).getClass().toString();
          rule.forEach(
              item -> {
                if (!ruleClassName.equals(item.getClass().toString())) {
                  throw new ValidatorException(String.format(VALIDATOR_INCONSISTENT, key));
                }
              });
        });
  }

  private void valueCheck(
      final CommandType commandType, final int value, final int min, final int max) {
    if (value >= min && value <= max) {
      return;
    } else {
      throw new InvalidCommandException(
          String.format(
              COMMAND_AND_VALUE_DO_NOT_MATCH, commandType, value, "min: " + min + " max: " + max));
    }
  }

  private void valueCheck(
      final CommandType commandType, final String value, final int min, final int max) {
    valueCheck(commandType, Integer.parseInt(value), min, max);
  }

  public CommandValidator() {
    prepareValidationMap();
  }
}
