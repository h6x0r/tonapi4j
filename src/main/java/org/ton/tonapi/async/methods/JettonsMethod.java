package org.ton.tonapi.async.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import org.ton.exception.TONAPIError;
import org.ton.schema.events.Event;
import org.ton.schema.jettons.JettonHolder;
import org.ton.schema.jettons.JettonHolders;
import org.ton.schema.jettons.JettonInfo;
import org.ton.schema.jettons.JettonTransferPayload;
import org.ton.schema.jettons.Jettons;
import org.ton.tonapi.async.AsyncTonapiClientBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class JettonsMethod extends AsyncTonapiClientBase {

    public JettonsMethod(AsyncTonapiClientBase client) {
        super(client);
    }

    /**
     * Get jetton metadata by jetton master address.
     *
     * @param jettonId jetton master address
     * @return CompletableFuture of JettonInfo object containing jetton metadata
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<JettonInfo> getInfo(String jettonId) throws TONAPIError {
        String method = String.format("v2/jettons/%s", jettonId);
        return this.get(method, null, null, new TypeReference<>() {
        });
    }

    /**
     * Get jetton's holders.
     *
     * @param jettonId jetton master address
     * @param limit    Number of records to return. Default is 1000.
     * @param offset   Offset for pagination. Default is 0.
     * @return CompletableFuture of JettonHolders object containing jetton holders
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<JettonHolders> getHolders(String jettonId, int limit, int offset) throws TONAPIError {
        String method = String.format("v2/jettons/%s/holders", jettonId);
        Map<String, Object> params = new HashMap<>();
        params.put("limit", limit);
        params.put("offset", offset);
        return this.get(method, params, null, new TypeReference<>() {
        });
    }

    /**
     * Get all jetton's holders.
     *
     * @param jettonId jetton master address
     * @return CompletableFuture of JettonHolders object containing all jetton holders
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<JettonHolders> getAllHolders(String jettonId) throws TONAPIError {
        List<JettonHolder> jettonHolders = new ArrayList<>();
        int limit = 1000;
        AtomicInteger offset = new AtomicInteger(0);
        CompletableFuture<JettonHolders> future = new CompletableFuture<>();

        fetchHolders(jettonId, limit, offset, jettonHolders, future);

        return future;
    }

    private void fetchHolders(String accountId, int limit, AtomicInteger offset,
                              List<JettonHolder> jettonHolders, CompletableFuture<JettonHolders> future) {
        this.getHolders(accountId, limit, offset.get())
                .thenAccept(result -> {
                    jettonHolders.addAll(result.getAddresses());
                    if (result.getAddresses().isEmpty() || result.getAddresses().size() < limit) {
                        future.complete(JettonHolders.builder()
                                .total(result.getTotal())
                                .addresses(jettonHolders)
                                .build());
                    } else {
                        offset.addAndGet(limit);
                        fetchHolders(accountId, limit, offset, jettonHolders, future);
                    }
                })
                .exceptionally(ex -> {
                    future.completeExceptionally(ex);
                    return null;
                });
    }

    /**
     * Get a list of all indexed jetton masters in the blockchain.
     *
     * @param limit  Number of records to return. Default is 100.
     * @param offset Offset for pagination. Default is 0.
     * @return CompletableFuture of Jettons object containing the list of jettons
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<Jettons> getAllJettons(int limit, int offset) throws TONAPIError {
        String method = "v2/jettons";
        Map<String, Object> params = new HashMap<>();
        params.put("limit", limit);
        params.put("offset", offset);
        return this.get(method, params, null, new TypeReference<>() {
        });
    }

    /**
     * Get only jetton transfers in the event.
     *
     * @param eventId Event ID or transaction hash in hex (without 0x) or base64url format
     * @return CompletableFuture of Event object containing jetton transfer events
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<Event> getJettonTransferEvent(String eventId) throws TONAPIError {
        String method = String.format("v2/events/%s/jettons", eventId);
        return this.get(method, null, null, new TypeReference<>() {
        });
    }

    /**
     * Get jetton's custom payload and state init required for transfer.
     *
     * @param jettonId  jetton master address
     * @param accountId Account ID (owner's address)
     * @return CompletableFuture of JettonTransferPayload object containing payload and state init
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<JettonTransferPayload> getJettonTransferPayload(String jettonId, String accountId) throws TONAPIError {
        String method = String.format("v2/jettons/%s/transfer/%s/payload", jettonId, accountId);
        return this.get(method, null, null, new TypeReference<>() {
        });
    }
}
