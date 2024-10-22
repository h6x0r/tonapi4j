package org.ton.tonapi.async.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import org.ton.exception.TONAPIError;
import org.ton.schema.traces.Trace;
import org.ton.tonapi.async.AsyncTonapiClientBase;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class TracesMethod extends AsyncTonapiClientBase {

    public TracesMethod(AsyncTonapiClientBase client) {
        super(client);
    }

    /**
     * Get the trace by trace ID or hash of any transaction in trace.
     *
     * @param traceId Trace ID or transaction hash in hex (without 0x) or base64url format
     * @return CompletableFuture of Trace object containing the trace data
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<Trace> getTrace(String traceId) throws TONAPIError {
        if (traceId.length() == 44) {
            byte[] decodedBytes = Base64.getUrlDecoder().decode(traceId);
            StringBuilder hexString = new StringBuilder();
            for (byte b : decodedBytes) {
                String hex = String.format("%02x", b);
                hexString.append(hex);
            }
            traceId = hexString.toString();
        }
        String method = String.format("v2/traces/%s", traceId);
        return this.get(method, null, null, new TypeReference<>() {
        });
    }

    /**
     * Emulate sending message to blockchain.
     *
     * @param body                 Map containing the 'boc' serialized to base64
     * @param ignoreSignatureCheck Optional parameter to ignore signature check
     * @return CompletableFuture of Trace object containing the emulated trace
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<Trace> emulate(Map<String, Object> body, Boolean ignoreSignatureCheck) throws TONAPIError {
        String method = "v2/traces/emulate";
        Map<String, Object> params = new HashMap<>();
        if (ignoreSignatureCheck != null && ignoreSignatureCheck) {
            params.put("ignore_signature_check", ignoreSignatureCheck);
        }
        return this.post(method, params.isEmpty() ? null : params, body, null, new TypeReference<>() {
        });
    }
}
