package pl.smarthouse.smartmodule.exceptions;

public class InvalidResponseException extends RuntimeException {
  public InvalidResponseException(final String message) {
    super(message);
  }
}
