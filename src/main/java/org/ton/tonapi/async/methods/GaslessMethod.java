package org.ton.tonapi.async.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import org.ton.exception.TONAPIError;
import org.ton.schema.gasless.GaslessConfig;
import org.ton.schema.gasless.SignRawParams;
import org.ton.tonapi.async.AsyncTonapiClientBase;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class GaslessMethod extends AsyncTonapiClientBase {

    public GaslessMethod(AsyncTonapiClientBase client) {
        super(client);
    }

    /**
     * Returns configuration of gasless transfers.
     *
     * @return CompletableFuture of GaslessConfig object containing gasless transfer configuration
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<GaslessConfig> getConfig() throws TONAPIError {
        String method = "v2/gasless/config";
        return this.get(method, null, null, new TypeReference<>() {
        });
    }

    /**
     * Returns estimated gas price.
     *
     * @param masterId Jetton Master ID.
     * @param body     The body should contain a JSON object with the required parameters.
     * @return CompletableFuture of SignRawParams object containing the estimated gas price
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<SignRawParams> estimateGasPrice(String masterId, Map<String, Object> body) throws TONAPIError {
        String method = String.format("v2/gasless/estimate/%s", masterId);
        return this.post(method, null, body, null, new TypeReference<>() {
        });
    }

    /**
     * Send message to the blockchain.
     *
     * @param body The body should contain a JSON object with the required parameters.
     * @return CompletableFuture<Boolean> true if the message was sent successfully
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<Boolean> send(Map<String, Object> body) throws TONAPIError {
        String method = "v2/gasless/send";
        return this.post(method, null, body, null, new TypeReference<>() {
                })
                .thenApply(response -> true);
    }
}
