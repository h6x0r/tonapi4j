package org.ton.tonapi.async.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import org.ton.exception.TONAPIError;
import org.ton.schema.events.Event;
import org.ton.tonapi.async.AsyncTonapiClientBase;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class EventsMethod extends AsyncTonapiClientBase {

    public EventsMethod(AsyncTonapiClientBase client) {
        super(client);
    }

    /**
     * Get an event either by event ID or a hash of any transaction in a trace.
     *
     * @param eventId        Event ID
     * @param acceptLanguage Accept-Language header value. Default is "en". Example -> ru-RU,ru;q=0.5
     * @return CompletableFuture of Event object containing the event data
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<Event> getEvent(String eventId, String acceptLanguage) throws TONAPIError {
        if (eventId.length() == 44) {
            // Decode Base64 URL-safe string
            byte[] decodedBytes = Base64.getUrlDecoder().decode(eventId);
            // Convert bytes to hex string
            StringBuilder hexString = new StringBuilder();
            for (byte b : decodedBytes) {
                String hex = String.format("%02x", b);
                hexString.append(hex);
            }
            eventId = hexString.toString();
        }
        String method = String.format("v2/events/%s", eventId);
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept-Language", acceptLanguage != null ? acceptLanguage : "en");
        return this.get(method, null, headers, new TypeReference<>() {
        });
    }

    /**
     * Emulate sending a message to the blockchain.
     *
     * @param boc                  the base64 serialized bag-of-cells Example -> te6ccgECBQEAARUAAkWIAWTtae+KgtbrX26Bep8JSq8lFLfGOoyGR/xwdjfvpvEaHg
     * @param acceptLanguage       Accept-Language header value. Default is "en". Example -> ru-RU,ru;q=0.5
     * @param ignoreSignatureCheck Optional parameter to ignore signature check
     * @return CompletableFuture of Event object containing the emulated event
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<Event> emulate(
            String boc,
            String acceptLanguage,
            Boolean ignoreSignatureCheck) throws TONAPIError {
        String method = "v2/events/emulate";

        Map<String, String> body = new HashMap<>();
        body.put("boc", boc);

        Map<String, Object> params = new HashMap<>();
        if (ignoreSignatureCheck != null && ignoreSignatureCheck) {
            params.put("ignore_signature_check", ignoreSignatureCheck);
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept-Language", acceptLanguage != null ? acceptLanguage : "en");
        return this.post(method, params.isEmpty() ? null : params, body, headers, new TypeReference<>() {
        });
    }
}
