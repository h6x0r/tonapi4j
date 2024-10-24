package org.ton.tonapi.async.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import org.ton.exception.TONAPIError;
import org.ton.schema.accounts.Accounts;
import org.ton.schema.message.MessageConsequences;
import org.ton.schema.wallet.AddressProof;
import org.ton.schema.wallet.BocParams;
import org.ton.tonapi.async.AsyncTonapiClientBase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class WalletMethod extends AsyncTonapiClientBase {

    public WalletMethod(AsyncTonapiClientBase client) {
        super(client);
    }

    /**
     * Get backup info.
     *
     * @param xTonconnectAuth X-TonConnect-Auth header value
     * @return CompletableFuture<String> containing the dump string or null if not available
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<String> getBackupInfo(String xTonconnectAuth) throws TONAPIError {
        String method = "v2/wallet/backup";
        Map<String, String> headers = new HashMap<>();
        headers.put("X-TonConnect-Auth", xTonconnectAuth);
        return this.get(method, null, headers, new TypeReference<Map<String, Object>>() {
                })
                .thenApply(response -> (String) response.get("dump"));
    }

    /**
     * Account verification and token issuance.
     *
     * @param addressProof Data expected from TON Connect
     * @return CompletableFuture<String> containing the token string
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<String> accountVerification(AddressProof addressProof) throws TONAPIError {
        String method = "v2/wallet/auth/proof";

        Map<String, Object> body = new HashMap<>();
        body.put("address", addressProof.getAddress());
        body.put("proof", addressProof.getProof());

        return this.post(method, null, body, null, new TypeReference<Map<String, Object>>() {
                })
                .thenApply(response -> (String) response.get("token"));
    }

    /**
     * Get wallet by public key.
     *
     * @param publicKey Public key
     * @return CompletableFuture<Accounts> object containing the accounts
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<Accounts> getByPublicKey(String publicKey) throws TONAPIError {
        String method = String.format("v2/pubkeys/%s/wallets", publicKey);
        return this.get(method, null, null, new TypeReference<Accounts>() {
        });
    }

    /**
     * Get account seqno.
     *
     * @param accountId Account ID
     * @return CompletableFuture<Integer> containing seqno or null if not available
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<Integer> getAccountSeqno(String accountId) throws TONAPIError {
        String method = String.format("v2/wallet/%s/seqno", accountId);
        return this.get(method, null, null, new TypeReference<Map<String, Object>>() {
                })
                .thenApply(response -> {
                    Object seqno = response.get("seqno");
                    return seqno != null ? ((Number) seqno).intValue() : null;
                });
    }

    /**
     * Emulate sending message to blockchain.
     *
     * @param bocParams      containing bag-of-cells serialized to base64/hex
     *                       and additional parameters to configure emulation
     * @param acceptLanguage Accept-Language header value. Default is "en". Example -> ru-RU,ru;q=0.5
     * @return CompletableFuture of MessageConsequences object containing the consequences
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<MessageConsequences> emulate(BocParams bocParams, String acceptLanguage) throws TONAPIError {
        String method = "v2/wallet/emulate";

        Map<String, Object> body = new HashMap<>();
        body.put("boc", bocParams.getBoc());
        body.put("params", bocParams.getParams());

        Map<String, String> headers = new HashMap<>();
        headers.put("Accept-Language", acceptLanguage != null ? acceptLanguage : "en");
        return this.post(method, null, body, headers, new TypeReference<MessageConsequences>() {
        });
    }
}
