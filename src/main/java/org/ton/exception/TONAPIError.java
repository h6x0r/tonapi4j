package org.ton.exception;

public class TONAPIError extends RuntimeException {

  public TONAPIError(String message) {
    super(message);
  }

  public TONAPIError(String message, Throwable cause) {
    super(message, cause);
  }
}
