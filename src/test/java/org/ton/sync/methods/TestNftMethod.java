package org.ton.sync.methods;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.ton.utils.Constants.ACCOUNT_ID_NFT;
import static org.ton.utils.Constants.COLLECTION_ADDRESS;
import static org.ton.utils.Constants.LIMIT;
import static org.ton.utils.Constants.OFFSET;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.ton.schema.events.AccountEvents;
import org.ton.schema.nft.NftCollection;
import org.ton.schema.nft.NftCollections;
import org.ton.schema.nft.NftItem;
import org.ton.schema.nft.NftItems;
import org.ton.sync.TonapiTestBase;

public class TestNftMethod extends TonapiTestBase {

  private static final List<String> ACCOUNT_IDS = Arrays.asList(
      "UQB8ANV_ynITQr1qHXADHDKYUAQ9VFcCRDZB7h4aPuPKuFtm",
      "UQC9_fN7oe8EoftOl5vcI3p1AIAQSKFnuvolC8--DHto19DS"
  );

  @Test
  public void testGetCollections() {
    NftCollections response = tonapi.getNft().getCollections();
    assertNotNull(response);
  }

  @Test
  public void testGetCollectionByCollectionAddress() {
    NftCollection response = tonapi.getNft().getCollectionByCollectionAddress(COLLECTION_ADDRESS);
    assertNotNull(response);
  }

  @Test
  public void testGetItemsByCollectionAddress() {
    NftItems response = tonapi.getNft()
        .getItemsByCollectionAddress(COLLECTION_ADDRESS, LIMIT, OFFSET);
    assertNotNull(response);
  }

  @Test
  @Disabled
  public void testGetAllItemsByCollectionAddress() {
    NftItems response = tonapi.getNft().getAllItemsByCollectionAddress(COLLECTION_ADDRESS);
    assertNotNull(response);
  }

  @Test
  public void testGetItemByAddress() {
    NftItem response = tonapi.getNft().getItemByAddress(ACCOUNT_ID_NFT);
    assertNotNull(response);
  }

  @Test
  public void testGetBulkItems() {
    NftItems response = tonapi.getNft().getBulkItems(ACCOUNT_IDS);
    assertNotNull(response);
  }

  @Test
  public void testGetNftHistory() {
    AccountEvents response = tonapi.getNft().getNftHistory(ACCOUNT_ID_NFT);
    assertNotNull(response);
  }
}
