package org.ton.sync.methods;

import org.junit.jupiter.api.Test;
import org.ton.schema.storage.StorageProviders;
import org.ton.sync.TonapiTestBase;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestStorageMethod extends TonapiTestBase {

    @Test
    public void testGetProviders() throws Exception {
        StorageProviders response = tonapi.getStorage().getProviders();
        assertNotNull(response);
    }
}
