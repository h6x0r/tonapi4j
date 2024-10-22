package org.ton.exception;

public class TONAPIUnauthorizedError extends TONAPIClientError {
    public TONAPIUnauthorizedError(String message) throws TONAPISSELimitReachedError {
        super("API key is missing or invalid. You can get an access token here https://tonconsole.com/");
        if (message != null && message.contains("limit of streaming")) {
            throw new TONAPISSELimitReachedError(message);
        }
    }
}
