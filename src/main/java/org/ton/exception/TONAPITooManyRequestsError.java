package org.ton.exception;

public class TONAPITooManyRequestsError extends TONAPIClientError {

  public TONAPITooManyRequestsError(String message) {
    super(message != null ? message
        : "Too many requests per second. Upgrade your plan on https://tonconsole.com/tonapi/pricing.");
  }
}
