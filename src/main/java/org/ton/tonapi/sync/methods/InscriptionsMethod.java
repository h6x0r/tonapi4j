package org.ton.tonapi.sync.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import org.ton.exception.TONAPIError;
import org.ton.schema.events.AccountEvents;
import org.ton.schema.inscriptions.InscriptionBalances;
import org.ton.schema.inscriptions.InscriptionCommentResponse;
import org.ton.tonapi.sync.TonapiClientBase;

import java.util.HashMap;
import java.util.Map;

public class InscriptionsMethod extends TonapiClientBase {

    public InscriptionsMethod(TonapiClientBase client) {
        super(client);
    }

    /**
     * Get all inscriptions by owner address. It's an experimental API and can be dropped in the future.
     *
     * @param accountId Wallet address
     * @param limit     Number of records to return. Default is 1000.
     * @param offset    Offset for pagination. Default is 0.
     * @return InscriptionBalances object containing the inscriptions
     * @throws TONAPIError if the request fails
     */
    public InscriptionBalances getAllInscriptions(
            String accountId,
            int limit,
            int offset) throws TONAPIError {
        String method = String.format("v2/experimental/accounts/%s/inscriptions", accountId);
        Map<String, Object> params = new HashMap<>();
        params.put("limit", limit);
        params.put("offset", offset);
        return this.get(method, params, null, new TypeReference<InscriptionBalances>() {
        });
    }

    /**
     * Get the transfer inscriptions history for an account. It's an experimental API and can be dropped in the future.
     *
     * @param accountId      Wallet address
     * @param beforeLt       Optional parameter to get events before the specified logical time (lt)
     * @param limit          Number of records to return. Default is 100.
     * @param acceptLanguage Accept-Language header value. Default is "en". Example -> ru-RU,ru;q=0.5
     * @return AccountEvents object containing the account events
     * @throws TONAPIError if the request fails
     */
    public AccountEvents getInscriptionHistory(
            String accountId,
            Long beforeLt,
            int limit,
            String acceptLanguage) throws TONAPIError {
        String method = String.format("v2/experimental/accounts/%s/inscriptions/history", accountId);
        Map<String, Object> params = new HashMap<>();
        params.put("limit", limit);
        if (beforeLt != null) {
            params.put("before_lt", beforeLt);
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept-Language", acceptLanguage != null ? acceptLanguage : "en");
        return this.get(method, params, headers, new TypeReference<AccountEvents>() {
        });
    }

    /**
     * Get the transfer inscriptions history for an account filtered by ticker.
     * It's an experimental API and can be dropped in the future.
     *
     * @param accountId      Wallet address
     * @param ticker         Token ticker
     * @param beforeLt       Optional parameter to get events before the specified logical time (lt)
     * @param limit          Number of records to return. Default is 100.
     * @param acceptLanguage Accept-Language header value. Default is "en". Example -> ru-RU,ru;q=0.5
     * @return AccountEvents object containing the account events
     * @throws TONAPIError if the request fails
     */
    public AccountEvents getInscriptionHistoryByTicker(
            String accountId,
            String ticker,
            Long beforeLt,
            int limit,
            String acceptLanguage) throws TONAPIError {
        String method = String.format("v2/experimental/accounts/%s/inscriptions/%s/history", accountId, ticker);
        Map<String, Object> params = new HashMap<>();
        params.put("limit", limit);
        if (beforeLt != null) {
            params.put("before_lt", beforeLt);
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept-Language", acceptLanguage != null ? acceptLanguage : "en");
        return this.get(method, params, headers, new TypeReference<AccountEvents>() {
        });
    }

    /**
     * Return comment for making operation with inscription. Please don't use it if you don't know what you are doing.
     *
     * @param who         Account address
     * @param amount      Amount of tokens
     * @param type        Type of the inscription ("ton20" or "gram20"). Default is "ton20".
     * @param destination Optional destination address
     * @param comment     Optional comment
     * @param operation   Operation type ("transfer"). Default is "transfer".
     * @param ticker      Token ticker. Default is "nano".
     * @return InscriptionCommentResponse object containing the comment and destination
     * @throws TONAPIError if the request fails
     */
    public InscriptionCommentResponse createInscriptionComment(
            String who,
            long amount,
            String type,
            String destination,
            String comment,
            String operation,
            String ticker) throws TONAPIError {
        String method = "v2/experimental/inscriptions/op-template";
        Map<String, Object> params = new HashMap<>();
        params.put("who", who);
        params.put("amount", amount);
        params.put("type", type != null ? type : "ton20");
        params.put("operation", operation != null ? operation : "transfer");
        params.put("ticker", ticker != null ? ticker : "nano");
        if (destination != null && !destination.isEmpty()) {
            params.put("destination", destination);
        }
        if (comment != null && !comment.isEmpty()) {
            params.put("comment", comment);
        }
        return this.get(method, params, null, new TypeReference<InscriptionCommentResponse>() {
        });
    }
}
