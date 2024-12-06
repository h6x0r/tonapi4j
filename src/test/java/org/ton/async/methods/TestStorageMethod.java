package org.ton.async.methods;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;
import org.ton.async.AsyncTonapiTestBase;
import org.ton.schema.storage.StorageProviders;

public class TestStorageMethod extends AsyncTonapiTestBase {

  @Test
  public void testGetProviders() throws Exception {
    CompletableFuture<StorageProviders> future = tonapi.getStorage().getProviders();
    StorageProviders response = future.get();
    assertNotNull(response);
  }
}
