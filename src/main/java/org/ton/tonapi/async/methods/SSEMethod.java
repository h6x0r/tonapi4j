package org.ton.tonapi.async.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import org.ton.exception.TONAPIError;
import org.ton.schema.events.TraceEventData;
import org.ton.schema.events.TransactionEventData;
import org.ton.schema.events.block.BlockEventData;
import org.ton.schema.events.mempool.MempoolEventData;
import org.ton.tonapi.async.AsyncTonapiClientBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SSEMethod extends AsyncTonapiClientBase {

    public SSEMethod(AsyncTonapiClientBase client) {
        super(client);
    }

    /**
     * Subscribes to transactions SSE events for the specified accounts.
     *
     * @param handler    A consumer function to handle the TransactionEventData
     * @param accounts   A list of account IDs. Use "ALL" to stream transactions for all accounts.
     * @param operations A list of operations to filter transactions.
     * @throws TONAPIError if the request fails
     */
    public void subscribeToTransactions(
            Consumer<TransactionEventData> handler,
            List<String> accounts,
            List<String> operations) throws TONAPIError {
        String method = "v2/sse/accounts/transactions";
        Map<String, Object> params = new HashMap<>();
        params.put("accounts", String.join(",", accounts));
        if (operations != null && !operations.isEmpty()) {
            params.put("operations", String.join(",", operations));
        }

        this.subscribe(method, params, new TypeReference<>() {
        }, handler);
    }

    /**
     * Subscribes to traces SSE events for the specified accounts.
     *
     * @param handler  A consumer function to handle the TraceEventData
     * @param accounts A list of account addresses to subscribe to
     * @throws TONAPIError if the request fails
     */
    public void subscribeToTraces(
            Consumer<TraceEventData> handler,
            List<String> accounts) throws TONAPIError {
        String method = "v2/sse/accounts/traces";
        Map<String, Object> params = new HashMap<>();
        params.put("accounts", String.join(",", accounts));

        this.subscribe(method, params, new TypeReference<>() {
        }, handler);
    }

    /**
     * Subscribes to mempool SSE events for the specified accounts.
     *
     * @param handler  A consumer function to handle the MempoolEventData
     * @param accounts A list of account addresses to subscribe to
     * @throws TONAPIError if the request fails
     */
    public void subscribeToMempool(
            Consumer<MempoolEventData> handler,
            List<String> accounts) throws TONAPIError {
        String method = "v2/sse/mempool";
        Map<String, Object> params = new HashMap<>();
        params.put("accounts", String.join(",", accounts));

        this.subscribe(method, params, new TypeReference<>() {
        }, handler);
    }

    /**
     * Subscribes to blocks SSE events for the specified workchains.
     *
     * @param handler   A consumer function to handle the BlockEventData
     * @param workchain The ID of the workchain to subscribe to. If null, subscribes to all workchains.
     * @throws TONAPIError if the request fails
     */
    public void subscribeToBlocks(
            Consumer<BlockEventData> handler,
            Long workchain) throws TONAPIError {
        String method = "v2/sse/blocks";
        Map<String, Object> params = new HashMap<>();
        if (workchain != null) {
            params.put("workchain", workchain);
        }

        this.subscribe(method, params, new TypeReference<>() {
        }, handler);
    }
}
