package org.ton.tonapi.sync.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import org.ton.exception.TONAPIError;
import org.ton.schema.events.AccountEvents;
import org.ton.schema.nft.NftCollection;
import org.ton.schema.nft.NftCollections;
import org.ton.schema.nft.NftItem;
import org.ton.schema.nft.NftItems;
import org.ton.tonapi.sync.TonapiClientBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NftMethod extends TonapiClientBase {

    public NftMethod(TonapiClientBase client) {
        super(client);
    }

    /**
     * Get NFT collections.
     *
     * @param limit  Number of records to return. Default is 15.
     * @param offset Offset for pagination. Default is 0.
     * @return NftCollections object containing the NFT collections.
     * @throws TONAPIError if the request fails
     */
    public NftCollections getCollections(int limit, int offset) throws TONAPIError {
        String method = "v2/nfts/collections";
        Map<String, Object> params = new HashMap<>();
        params.put("limit", limit);
        params.put("offset", offset);
        return this.get(method, params, null, new TypeReference<NftCollections>() {
        });
    }

    /**
     * Get NFT collections with default parameters.
     *
     * @return NftCollections object containing the NFT collections.
     * @throws TONAPIError if the request fails
     */
    public NftCollections getCollections() throws TONAPIError {
        return getCollections(15, 0);
    }

    /**
     * Get NFT collection by collection address.
     *
     * @param accountId Account ID
     * @return NftCollection object containing the NFT collection information
     * @throws TONAPIError if the request fails
     */
    public NftCollection getCollectionByCollectionAddress(String accountId) throws TONAPIError {
        String method = String.format("v2/nfts/collections/%s", accountId);
        return this.get(method, null, null, new TypeReference<NftCollection>() {
        });
    }

    /**
     * Get NFT items from collection by collection address.
     *
     * @param accountId Account ID
     * @param limit     Number of records to return. Default is 1000.
     * @param offset    Offset for pagination. Default is 0.
     * @return NftItems object containing the NFT items.
     * @throws TONAPIError if the request fails
     */
    public NftItems getItemsByCollectionAddress(String accountId, int limit, int offset) throws TONAPIError {
        String method = String.format("v2/nfts/collections/%s/items", accountId);
        Map<String, Object> params = new HashMap<>();
        params.put("limit", limit);
        params.put("offset", offset);
        return this.get(method, params, null, new TypeReference<NftItems>() {
        });
    }

    /**
     * Get all NFT items from collection by collection address.
     *
     * @param accountId Account ID
     * @return NftItems object containing all NFT items.
     * @throws TONAPIError if the request fails
     */
    public NftItems getAllItemsByCollectionAddress(String accountId) throws TONAPIError {
        List<NftItem> nftItems = new ArrayList<>();
        int offset = 0;
        int limit = 1000;
        NftItems result;

        do {
            result = this.getItemsByCollectionAddress(accountId, limit, offset);
            if (result.getNftItems() != null) {
                nftItems.addAll(result.getNftItems());
            }
            offset += limit;
        } while (result.getNftItems() != null && !result.getNftItems().isEmpty());

        return NftItems.builder()
                .nftItems(nftItems)
                .build();
    }

    /**
     * Get NFT item by its address.
     *
     * @param accountId Account ID
     * @return NftItem object containing the NFT item information.
     * @throws TONAPIError if the request fails
     */
    public NftItem getItemByAddress(String accountId) throws TONAPIError {
        String method = String.format("v2/nfts/%s", accountId);
        return this.get(method, null, null, new TypeReference<NftItem>() {
        });
    }

    /**
     * Get NFT items by their addresses.
     *
     * @param accountIds List of account IDs.
     * @return NftItems object containing the NFT items.
     * @throws TONAPIError if the request fails
     */
    public NftItems getBulkItems(List<String> accountIds) throws TONAPIError {
        String method = "v2/nfts/_bulk";
        Map<String, Object> body = new HashMap<>();
        body.put("account_ids", accountIds);
        return this.post(method, null, body, null, new TypeReference<NftItems>() {
        });
    }

    /**
     * Get the transfer NFTs history for account.
     *
     * @param accountId      Account ID
     * @param limit          Number of records to return. Default is 100.
     * @param beforeLt       Optional parameter to get events before the specified logical time (lt)
     * @param acceptLanguage Accept-Language header value. Default is "en". Example -> ru-RU,ru;q=0.5
     * @param startDate      Optional start date (timestamp)
     * @param endDate        Optional end date (timestamp)
     * @return AccountEvents object containing the account events
     * @throws TONAPIError if the request fails
     */
    public AccountEvents getNftHistory(
            String accountId,
            int limit,
            Long beforeLt,
            String acceptLanguage,
            Long startDate,
            Long endDate) throws TONAPIError {
        String method = String.format("v2/nfts/%s/history", accountId);
        Map<String, Object> params = new HashMap<>();
        params.put("limit", limit);
        if (beforeLt != null) {
            params.put("before_lt", beforeLt);
        }
        if (startDate != null) {
            params.put("start_date", startDate);
        }
        if (endDate != null) {
            params.put("end_date", endDate);
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept-Language", acceptLanguage != null ? acceptLanguage : "en");
        return this.get(method, params, headers, new TypeReference<AccountEvents>() {
        });
    }

    /**
     * Get the transfer NFTs history for account with default parameters.
     *
     * @param accountId Account ID
     * @return AccountEvents object containing the account events
     * @throws TONAPIError if the request fails
     */
    public AccountEvents getNftHistory(String accountId) throws TONAPIError {
        return getNftHistory(accountId, 100, null, "en", null, null);
    }
}
