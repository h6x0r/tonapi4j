package org.ton.tonapi.sync.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import org.ton.exception.TONAPIError;
import org.ton.schema.blockchain.DecodedMessage;
import org.ton.schema.events.AccountEvent;
import org.ton.schema.events.Event;
import org.ton.schema.message.MessageConsequences;
import org.ton.schema.traces.Trace;
import org.ton.tonapi.sync.TonapiClientBase;

import java.util.HashMap;
import java.util.Map;

public class EmulateMethod extends TonapiClientBase {

    public EmulateMethod(TonapiClientBase client) {
        super(client);
    }

    /**
     * Decode a given message. Only external incoming messages can be decoded currently.
     *
     * @param body Map containing the 'boc' key with the base64 serialized bag-of-cells.
     *             Example:
     *             {
     *             "boc": "te6ccgECBQEAARUAAkWIAWTtae+KgtbrX26Bep8JSq8lFLfGOoyGR/xwdjfvpvEaHg"
     *             }
     * @return DecodedMessage object containing the decoded message.
     * @throws TONAPIError if the request fails
     */
    public DecodedMessage decodeMessage(Map<String, Object> body) throws TONAPIError {
        String method = "v2/message/decode";
        return this.post(method, null, body, null, new TypeReference<>() {
        });
    }

    /**
     * Emulate sending a message to the blockchain and get the event.
     *
     * @param body                 Map containing the 'boc' key with the base64 serialized bag-of-cells.
     *                             Example:
     *                             {
     *                             "boc": "te6ccgECBQEAARUAAkWIAWTtae+KgtbrX26Bep8JSq8lFLfGOoyGR/xwdjfvpvEaHg"
     *                             }
     * @param acceptLanguage       The Accept-Language header value. Default is "en".
     * @param ignoreSignatureCheck Optional parameter to ignore signature check.
     * @return Event object containing the emulated event.
     * @throws TONAPIError if the request fails
     */
    public Event emulateEvents(
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
     *                             Example:
     *                             {
     *                             "boc": "te6ccgECBQEAARUAAkWIAWTtae+KgtbrX26Bep8JSq8lFLfGOoyGR/xwdjfvpvEaHg"
     *                             }
     * @param ignoreSignatureCheck Optional parameter to ignore signature check.
     * @return Trace object containing the emulated trace.
     * @throws TONAPIError if the request fails
     */
    public Trace emulateTraces(
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
     *                       Example:
     *                       {
     *                       "boc": "te6ccgECBQEAARUAAkWIAWTtae+KgtbrX26Bep8JSq8lFLfGOoyGR/xwdjfvpvEaHg",
     *                       "params": [
     *                       {
     *                       "address": "0:97146a46acc2654y27947f14c4a4b14273e954f78bc017790b41208b0043200b",
     *                       "balance": 10000000000
     *                       }
     *                       ]
     *                       }
     * @param acceptLanguage The Accept-Language header value. Default is "en".
     * @return MessageConsequences object containing the consequences of the message.
     * @throws TONAPIError if the request fails
     */
    public MessageConsequences emulateWallet(
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
     *                       Example:
     *                       {
     *                       "boc": "te6ccgECBQEAARUAAkWIAWTtae+KgtbrX26Bep8JSq8lFLfGOoyGR/xwdjfvpvEaHg"
     *                       }
     * @param acceptLanguage The Accept-Language header value. Default is "en".
     * @return AccountEvent object containing the emulated account event.
     * @throws TONAPIError if the request fails
     */
    public AccountEvent emulateAccountEvent(
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
