package org.ton.tonapi.async.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import org.ton.exception.TONAPIError;
import org.ton.schema.events.AccountEvents;
import org.ton.schema.nft.NftCollection;
import org.ton.schema.nft.NftCollections;
import org.ton.schema.nft.NftItem;
import org.ton.schema.nft.NftItems;
import org.ton.tonapi.async.AsyncTonapiClientBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class NftMethod extends AsyncTonapiClientBase {

    public NftMethod(AsyncTonapiClientBase client) {
        super(client);
    }

    /**
     * Get NFT collections.
     *
     * @param limit  Number of records to return. Default is 15.
     * @param offset Offset for pagination. Default is 0.
     * @return CompletableFuture of NftCollections object containing the NFT collections.
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<NftCollections> getCollections(int limit, int offset) throws TONAPIError {
        String method = "v2/nfts/collections";
        Map<String, Object> params = new HashMap<>();
        params.put("limit", limit);
        params.put("offset", offset);
        return this.get(method, params, null, new TypeReference<>() {
        });
    }

    /**
     * Get NFT collections with default parameters.
     *
     * @return CompletableFuture of NftCollections object containing the NFT collections.
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<NftCollections> getCollections() throws TONAPIError {
        return getCollections(15, 0);
    }

    /**
     * Get NFT collection by collection address.
     *
     * @param accountId Account ID
     * @return CompletableFuture of NftCollection object containing the NFT collection information
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<NftCollection> getCollectionByCollectionAddress(String accountId) throws TONAPIError {
        String method = String.format("v2/nfts/collections/%s", accountId);
        return this.get(method, null, null, new TypeReference<>() {
        });
    }

    /**
     * Get NFT items from collection by collection address.
     *
     * @param accountId Account ID
     * @param limit     Number of records to return. Default is 1000.
     * @param offset    Offset for pagination. Default is 0.
     * @return CompletableFuture of NftItems object containing the NFT items.
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<NftItems> getItemsByCollectionAddress(String accountId, int limit, int offset) throws TONAPIError {
        String method = String.format("v2/nfts/collections/%s/items", accountId);
        Map<String, Object> params = new HashMap<>();
        params.put("limit", limit);
        params.put("offset", offset);
        return this.get(method, params, null, new TypeReference<>() {
        });
    }

    /**
     * Get all NFT items from collection by collection address.
     *
     * @param accountId Account ID
     * @return CompletableFuture of NftItems object containing all NFT items.
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<NftItems> getAllItemsByCollectionAddress(String accountId) throws TONAPIError {
        List<NftItem> nftItems = new ArrayList<>();
        int limit = 255;
        AtomicInteger offset = new AtomicInteger(0);
        CompletableFuture<NftItems> future = new CompletableFuture<>();

        fetchItems(accountId, limit, offset, nftItems, future);

        return future;
    }

    private void fetchItems(String accountId, int limit, AtomicInteger offset,
                            List<NftItem> nftItems, CompletableFuture<NftItems> future) {
        this.getItemsByCollectionAddress(accountId, limit, offset.get())
                .thenAccept(result -> {
                    if (result.getNftItems() != null) {
                        nftItems.addAll(result.getNftItems());
                    }
                    if (result.getNftItems() == null || result.getNftItems().isEmpty() || result.getNftItems().size() < limit) {
                        future.complete(NftItems.builder()
                                .nftItems(nftItems)
                                .build());
                    } else {
                        offset.addAndGet(limit);
                        fetchItems(accountId, limit, offset, nftItems, future);
                    }
                })
                .exceptionally(ex -> {
                    future.completeExceptionally(ex);
                    return null;
                });
    }

    /**
     * Get NFT item by its address.
     *
     * @param accountId Account ID
     * @return CompletableFuture of NftItem object containing the NFT item information.
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<NftItem> getItemByAddress(String accountId) throws TONAPIError {
        String method = String.format("v2/nfts/%s", accountId);
        return this.get(method, null, null, new TypeReference<>() {
        });
    }

    /**
     * Get NFT items by their addresses.
     *
     * @param accountIds List of account IDs.
     * @return CompletableFuture of NftItems object containing the NFT items.
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<NftItems> getBulkItems(List<String> accountIds) throws TONAPIError {
        String method = "v2/nfts/_bulk";
        Map<String, Object> body = new HashMap<>();
        body.put("account_ids", accountIds);

        return this.post(method, null, body, null, new TypeReference<>() {
        });
    }

    /**
     * Get the transfer NFTs history for account.
     *
     * @param accountId      Account ID
     * @param limit          Number of records to return. Default is 100.
     * @param beforeLt       Optional parameter to get events before the specified logical time (lt)
     * @param acceptLanguage Accept-Language header value. Default is "en".
     * @param startDate      Optional start date (timestamp)
     * @param endDate        Optional end date (timestamp)
     * @return CompletableFuture of AccountEvents object containing the account events
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<AccountEvents> getNftHistory(
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
        return this.get(method, params, headers, new TypeReference<>() {
        });
    }

    /**
     * Get the transfer NFTs history for account with default parameters.
     *
     * @param accountId Account ID
     * @return CompletableFuture of AccountEvents object containing the account events
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<AccountEvents> getNftHistory(String accountId) throws TONAPIError {
        return getNftHistory(accountId, 100, null, "en", null, null);
    }
}
