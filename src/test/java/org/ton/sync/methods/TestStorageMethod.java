package org.ton.sync.methods;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.ton.schema.storage.StorageProviders;
import org.ton.sync.TonapiTestBase;

public class TestStorageMethod extends TonapiTestBase {

  @Test
  public void testGetProviders() {
    StorageProviders response = tonapi.getStorage().getProviders();
    assertNotNull(response);
  }
}
