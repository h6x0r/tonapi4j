package org.ton.tonapi.sync.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import org.ton.exception.TONAPIError;
import org.ton.schema.events.Event;
import org.ton.schema.jettons.JettonHolder;
import org.ton.schema.jettons.JettonHolders;
import org.ton.schema.jettons.JettonInfo;
import org.ton.schema.jettons.JettonTransferPayload;
import org.ton.schema.jettons.Jettons;
import org.ton.tonapi.sync.TonapiClientBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JettonsMethod extends TonapiClientBase {

    public JettonsMethod(TonapiClientBase client) {
        super(client);
    }

    /**
     * Get jetton metadata by jetton master address.
     *
     * @param accountId Account ID (jetton master address)
     * @return JettonInfo object containing jetton metadata
     * @throws TONAPIError if the request fails
     */
    public JettonInfo getInfo(String accountId) throws TONAPIError {
        String method = String.format("v2/jettons/%s", accountId);
        return this.get(method, null, null, new TypeReference<JettonInfo>() {
        });
    }

    /**
     * Get jetton's holders.
     *
     * @param accountId Account ID (jetton master address)
     * @param limit     Number of records to return. Default is 1000.
     * @param offset    Offset for pagination. Default is 0.
     * @return JettonHolders object containing jetton holders
     * @throws TONAPIError if the request fails
     */
    public JettonHolders getHolders(String accountId, int limit, int offset) throws TONAPIError {
        String method = String.format("v2/jettons/%s/holders", accountId);
        Map<String, Object> params = new HashMap<>();
        params.put("limit", limit);
        params.put("offset", offset);
        return this.get(method, params, null, new TypeReference<JettonHolders>() {
        });
    }

    /**
     * Get all jetton's holders.
     *
     * @param accountId Account ID (jetton master address)
     * @return JettonHolders object containing all jetton holders
     * @throws TONAPIError if the request fails
     */
    public JettonHolders getAllHolders(String accountId) throws TONAPIError {
        List<JettonHolder> jettonHolders = new ArrayList<>();
        int offset = 0;
        int limit = 1000;
        JettonHolders result;

        do {
            result = this.getHolders(accountId, limit, offset);
            jettonHolders.addAll(result.getAddresses());
            offset += limit;
        } while (!result.getAddresses().isEmpty());

        return JettonHolders.builder()
                .total(result.getTotal())
                .addresses(jettonHolders)
                .build();
    }

    /**
     * Get a list of all indexed jetton masters in the blockchain.
     *
     * @param limit  Number of records to return. Default is 100.
     * @param offset Offset for pagination. Default is 0.
     * @return Jettons object containing the list of jettons
     * @throws TONAPIError if the request fails
     */
    public Jettons getAllJettons(int limit, int offset) throws TONAPIError {
        String method = "v2/jettons";
        Map<String, Object> params = new HashMap<>();
        params.put("limit", limit);
        params.put("offset", offset);
        return this.get(method, params, null, new TypeReference<Jettons>() {
        });
    }

    /**
     * Get only jetton transfers in the event.
     *
     * @param eventId Event ID or transaction hash in hex (without 0x) or base64url format
     * @return Event object containing jetton transfer events
     * @throws TONAPIError if the request fails
     */
    public Event getJettonTransferEvent(String eventId) throws TONAPIError {
        String method = String.format("v2/events/%s/jettons", eventId);
        return this.get(method, null, null, new TypeReference<Event>() {
        });
    }

    /**
     * Get jetton's custom payload and state init required for transfer.
     *
     * @param jettonId  Jetton ID (jetton master address)
     * @param accountId Account ID (owner's address)
     * @return JettonTransferPayload object containing payload and state init
     * @throws TONAPIError if the request fails
     */
    public JettonTransferPayload getJettonTransferPayload(String jettonId, String accountId) throws TONAPIError {
        String method = String.format("v2/jettons/%s/transfer/%s/payload", jettonId, accountId);
        return this.get(method, null, null, new TypeReference<JettonTransferPayload>() {
        });
    }
}
