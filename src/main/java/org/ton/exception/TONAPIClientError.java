package org.ton.exception;

public class TONAPIClientError extends TONAPIError {
    public TONAPIClientError(String message) {
        super(message);
    }

    public TONAPIClientError(String message, Throwable cause) {
        super(message, cause);
    }
}
