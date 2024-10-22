package org.ton.tonapi.sync.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import org.ton.exception.TONAPIError;
import org.ton.schema.gasless.GaslessConfig;
import org.ton.schema.gasless.SignRawParams;
import org.ton.tonapi.sync.TonapiClientBase;

import java.util.Map;

public class GaslessMethod extends TonapiClientBase {

    public GaslessMethod(TonapiClientBase client) {
        super(client);
    }

    /**
     * Returns configuration of gasless transfers.
     *
     * @return GaslessConfig object containing gasless transfer configuration
     * @throws TONAPIError if the request fails
     */
    public GaslessConfig getConfig() throws TONAPIError {
        String method = "v2/gasless/config";
        return this.get(method, null, null, new TypeReference<>() {
        });
    }

    /**
     * Returns estimated gas price.
     *
     * @param masterId Jetton Master ID.
     * @param body     The body should contain a JSON object with the following structure:
     *                 {
     *                 "wallet_address": "string",
     *                 "wallet_public_key": "string",
     *                 "messages": [
     *                 {
     *                 "boc": "string"
     *                 }
     *                 ]
     *                 }
     * @return SignRawParams object containing the estimated gas price
     * @throws TONAPIError if the request fails
     */
    public SignRawParams estimateGasPrice(String masterId, Map<String, Object> body) throws TONAPIError {
        String method = String.format("v2/gasless/estimate/%s", masterId);
        return this.post(method, null, body, null, new TypeReference<>() {
        });
    }

    /**
     * Send message to the blockchain.
     *
     * @param body The body should contain a JSON object with the following structure:
     *             {
     *             "wallet_public_key": "string",  // The public key of the wallet.
     *             "boc": "string"  // A single BOC or a batch of BOCs serialized in base64.
     *             }
     * @return true if the message was sent successfully
     * @throws TONAPIError if the request fails
     */
    public boolean send(Map<String, Object> body) throws TONAPIError {
        String method = "v2/gasless/send";
        this.post(method, null, body, null, new TypeReference<>() {
        });
        return true;
    }
}
