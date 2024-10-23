package org.ton.tonapi.async.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import org.glassfish.grizzly.utils.Pair;
import org.ton.exception.TONAPIError;
import org.ton.schema.gasless.GaslessConfig;
import org.ton.schema.gasless.SignRawParams;
import org.ton.tonapi.async.AsyncTonapiClientBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

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
     * @param walletAddress Wallet address the tx is going to be made from
     * @param walletPublicKey The public key of the wallet
     * @param messageList List of messages in a form of BOC
     * @return CompletableFuture of SignRawParams object containing the estimated gas price
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<SignRawParams> estimateGasPrice(String masterId, String walletAddress, String walletPublicKey, List<String> messageList) throws TONAPIError {
        String method = String.format("v2/gasless/estimate/%s", masterId);

        List<Pair<String, String>> messages = messageList.stream()
                .map(msg -> new Pair<>("boc", msg))
                .collect(Collectors.toList());

        Map<String, Object> body = new HashMap<>();
        body.put("wallet_address", walletAddress);
        body.put("wallet_public_key", walletPublicKey);
        body.put("messages", messages);

        return this.post(method, null, body, null, new TypeReference<>() {
        });
    }

    /**
     * Send message to the blockchain.
     *
     * @param walletPublicKey The public key of the wallet.
     * @param boc A single BOC or a batch of BOCs serialized in base64.
     * @return CompletableFuture<Boolean> true if the message was sent successfully
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<Boolean> send(String walletPublicKey, String boc) throws TONAPIError {
        String method = "v2/gasless/send";

        Map<String, String> body = new HashMap<>();
        body.put("wallet_public_key", walletPublicKey);
        body.put("boc", boc);

        return this.post(method, null, body, null, new TypeReference<>() {
                })
                .thenApply(response -> true);
    }
}
