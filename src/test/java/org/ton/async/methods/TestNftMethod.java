package org.ton.async.methods;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.ton.async.AsyncTonapiTestBase;
import org.ton.schema.events.AccountEvents;
import org.ton.schema.nft.NftCollection;
import org.ton.schema.nft.NftCollections;
import org.ton.schema.nft.NftItem;
import org.ton.schema.nft.NftItems;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.ton.utils.Constants.ACCOUNT_ID_NFT;
import static org.ton.utils.Constants.COLLECTION_ADDRESS;
import static org.ton.utils.Constants.LIMIT;
import static org.ton.utils.Constants.OFFSET;

public class TestNftMethod extends AsyncTonapiTestBase {

    private static final List<String> ACCOUNT_IDS = Arrays.asList(
            "UQB8ANV_ynITQr1qHXADHDKYUAQ9VFcCRDZB7h4aPuPKuFtm",
            "UQC9_fN7oe8EoftOl5vcI3p1AIAQSKFnuvolC8--DHto19DS"
    );

    @Test
    public void testGetCollections() throws Exception {
        CompletableFuture<NftCollections> future = tonapi.getNft().getCollections();
        NftCollections response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetCollectionByCollectionAddress() throws Exception {
        CompletableFuture<NftCollection> future = tonapi.getNft().getCollectionByCollectionAddress(COLLECTION_ADDRESS);
        NftCollection response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetItemsByCollectionAddress() throws Exception {
        CompletableFuture<NftItems> future = tonapi.getNft().getItemsByCollectionAddress(COLLECTION_ADDRESS, LIMIT, OFFSET);
        NftItems response = future.get();
        assertNotNull(response);
    }

    @Test
    @Disabled
    public void testGetAllItemsByCollectionAddress() throws Exception {
        CompletableFuture<NftItems> future = tonapi.getNft().getAllItemsByCollectionAddress(COLLECTION_ADDRESS);
        NftItems response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetItemByAddress() throws Exception {
        CompletableFuture<NftItem> future = tonapi.getNft().getItemByAddress(ACCOUNT_ID_NFT);
        NftItem response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetBulkItems() throws Exception {
        CompletableFuture<NftItems> future = tonapi.getNft().getBulkItems(ACCOUNT_IDS);
        NftItems response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetNftHistory() throws Exception {
        CompletableFuture<AccountEvents> future = tonapi.getNft().getNftHistory(ACCOUNT_ID_NFT);
        AccountEvents response = future.get();
        assertNotNull(response);
    }
}
