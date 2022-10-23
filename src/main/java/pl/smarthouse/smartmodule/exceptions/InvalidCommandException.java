package pl.smarthouse.smartmodule.exceptions;

public class InvalidCommandException extends RuntimeException {
  public InvalidCommandException(final String message) {
    super(message);
  }
}
