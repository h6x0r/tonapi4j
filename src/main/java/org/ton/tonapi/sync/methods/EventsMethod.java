package org.ton.tonapi.sync.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import org.ton.exception.TONAPIError;
import org.ton.schema.events.Event;
import org.ton.tonapi.sync.TonapiClientBase;

public class EventsMethod extends TonapiClientBase {

  public EventsMethod(TonapiClientBase client) {
    super(client);
  }

  /**
   * Get an event either by event ID or a hash of any transaction in a trace. An event is built on
   * top of a trace which is a series of transactions caused by one inbound message. TonAPI looks
   * for known patterns inside the trace and splits the trace into actions, where a single action
   * represents a meaningful high-level operation like a Jetton Transfer or an NFT Purchase. Actions
   * are expected to be shown to users. It is advised not to build any logic on top of actions
   * because actions can be changed at any time.
   *
   * @param eventId        Event ID
   * @param acceptLanguage Accept-Language header value. Default is "en". Example -> ru-RU,ru;q=0.5
   * @return Event object containing the event data
   * @throws TONAPIError if the request fails
   */
  public Event getEvent(String eventId, String acceptLanguage) throws TONAPIError {
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
    return this.get(method, null, headers, new TypeReference<Event>() {
    });
  }

  /**
   * Emulate sending a message to the blockchain.
   *
   * @param boc                  the base64 serialized bag-of-cells Example ->
   *                             te6ccgECBQEAARUAAkWIAWTtae+KgtbrX26Bep8JSq8lFLfGOoyGR/xwdjfvpvEaHg
   * @param acceptLanguage       Accept-Language header value. Default is "en". Example ->
   *                             ru-RU,ru;q=0.5
   * @param ignoreSignatureCheck Optional parameter to ignore signature check
   * @return Event object containing the emulated event
   * @throws TONAPIError if the request fails
   */
  public Event emulate(
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
    return this.post(method, params.isEmpty() ? null : params, body, headers,
        new TypeReference<Event>() {
        });
  }
}
