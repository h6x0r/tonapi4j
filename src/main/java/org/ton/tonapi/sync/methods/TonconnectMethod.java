package org.ton.tonapi.sync.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import org.ton.exception.TONAPIError;
import org.ton.schema.tonconnect.AccountInfoByStateInit;
import org.ton.schema.tonconnect.TonconnectPayload;
import org.ton.tonapi.sync.TonapiClientBase;

import java.util.HashMap;
import java.util.Map;

public class TonconnectMethod extends TonapiClientBase {

    public TonconnectMethod(TonapiClientBase client) {
        super(client);
    }

    /**
     * Get a payload for further token receipt.
     *
     * @return TonconnectPayload object containing the payload
     * @throws TONAPIError if the request fails
     */
    public TonconnectPayload getPayload() throws TONAPIError {
        String method = "v2/tonconnect/payload";
        return this.get(method, null, null, new TypeReference<>() {
        });
    }

    /**
     * Get account info by state init.
     *
     * @param stateInit The state init data
     * @return AccountInfoByStateInit object containing the account info
     * @throws TONAPIError if the request fails
     */
    public AccountInfoByStateInit getInfoByStateInit(String stateInit) throws TONAPIError {
        String method = "v2/tonconnect/stateinit";
        Map<String, Object> body = new HashMap<>();
        body.put("state_init", stateInit);
        return this.post(method, null, body, null, new TypeReference<>() {
        });
    }
}
