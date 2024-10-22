package org.ton.tonapi.async.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import org.ton.exception.TONAPIError;
import org.ton.schema.blockchain.DecodedMessage;
import org.ton.schema.events.AccountEvent;
import org.ton.schema.events.Event;
import org.ton.schema.message.MessageConsequences;
import org.ton.schema.traces.Trace;
import org.ton.tonapi.async.AsyncTonapiClientBase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class EmulateMethod extends AsyncTonapiClientBase {

    public EmulateMethod(AsyncTonapiClientBase client) {
        super(client);
    }

    /**
     * Decode a given message. Only external incoming messages can be decoded currently.
     *
     * @param body Map containing the 'boc' key with the base64 serialized bag-of-cells.
     * @return CompletableFuture of DecodedMessage object containing the decoded message.
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<DecodedMessage> decodeMessage(Map<String, Object> body) throws TONAPIError {
        String method = "v2/message/decode";
        return this.post(method, null, body, null, new TypeReference<>() {
        });
    }

    /**
     * Emulate sending a message to the blockchain and get the event.
     *
     * @param body                 Map containing the 'boc' key with the base64 serialized bag-of-cells.
     * @param acceptLanguage       The Accept-Language header value. Default is "en".
     * @param ignoreSignatureCheck Optional parameter to ignore signature check.
     * @return CompletableFuture of Event object containing the emulated event.
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<Event> emulateEvents(
            Map<String, Object> body,
            String acceptLanguage,
            Boolean ignoreSignatureCheck) throws TONAPIError {
        String method = "v2/events/emulate";
        Map<String, Object> params = new HashMap<>();
        if (ignoreSignatureCheck != null) {
            params.put("ignore_signature_check", ignoreSignatureCheck);
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept-Language", acceptLanguage != null ? acceptLanguage : "en");
        return this.post(method, params.isEmpty() ? null : params, body, headers, new TypeReference<>() {
        });
    }

    /**
     * Emulate sending a message to the blockchain and get the trace.
     *
     * @param body                 Map containing the 'boc' key with the base64 serialized bag-of-cells.
     * @param ignoreSignatureCheck Optional parameter to ignore signature check.
     * @return CompletableFuture of Trace object containing the emulated trace.
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<Trace> emulateTraces(
            Map<String, Object> body,
            Boolean ignoreSignatureCheck) throws TONAPIError {
        String method = "v2/traces/emulate";
        Map<String, Object> params = new HashMap<>();
        if (ignoreSignatureCheck != null) {
            params.put("ignore_signature_check", ignoreSignatureCheck);
        }
        return this.post(method, params.isEmpty() ? null : params, body, null, new TypeReference<>() {
        });
    }

    /**
     * Emulate sending a message to the blockchain as a wallet.
     *
     * @param body           Map containing the 'boc' and additional parameters to configure emulation.
     * @param acceptLanguage The Accept-Language header value. Default is "en".
     * @return CompletableFuture of MessageConsequences object containing the consequences of the message.
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<MessageConsequences> emulateWallet(
            Map<String, Object> body,
            String acceptLanguage) throws TONAPIError {
        String method = "v2/wallet/emulate";
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept-Language", acceptLanguage != null ? acceptLanguage : "en");
        return this.post(method, null, body, headers, new TypeReference<>() {
        });
    }

    /**
     * Emulate sending a message to the blockchain for a specific account and get the account event.
     *
     * @param accountId      Account ID
     * @param body           Map containing the 'boc' key with the base64 serialized bag-of-cells.
     * @param acceptLanguage The Accept-Language header value. Default is "en".
     * @return CompletableFuture of AccountEvent object containing the emulated account event.
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<AccountEvent> emulateAccountEvent(
            String accountId,
            Map<String, Object> body,
            String acceptLanguage) throws TONAPIError {
        String method = String.format("v2/accounts/%s/events/emulate", accountId);
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept-Language", acceptLanguage != null ? acceptLanguage : "en");
        return this.post(method, null, body, headers, new TypeReference<>() {
        });
    }
}
