package org.ton.async.methods;

import org.junit.jupiter.api.Test;
import org.ton.async.AsyncTonapiTestBase;
import org.ton.schema.storage.StorageProviders;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestStorageMethod extends AsyncTonapiTestBase {

    @Test
    public void testGetProviders() throws Exception {
        CompletableFuture<StorageProviders> future = tonapi.getStorage().getProviders();
        StorageProviders response = future.get();
        assertNotNull(response);
    }
}
