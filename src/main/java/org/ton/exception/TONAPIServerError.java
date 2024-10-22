package org.ton.exception;

public class TONAPIServerError extends TONAPIError {
    public TONAPIServerError(String message) {
        super(message);
    }

    public TONAPIServerError(String message, Throwable cause) {
        super(message, cause);
    }
}
