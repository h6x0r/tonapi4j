package org.ton.async.methods;

import org.junit.jupiter.api.Test;
import org.ton.async.AsyncTonapiTestBase;
import org.ton.schema.tonconnect.AccountInfoByStateInit;
import org.ton.schema.tonconnect.TonconnectPayload;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestTonconnectMethod extends AsyncTonapiTestBase {

    private static final String STATE_INIT = "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";

    @Test
    public void testGetPayload() throws Exception {
        CompletableFuture<TonconnectPayload> future = tonapi.getTonconnect().getPayload();
        TonconnectPayload response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetInfoByStateInit() throws Exception {
        CompletableFuture<AccountInfoByStateInit> future = tonapi.getTonconnect().getInfoByStateInit(STATE_INIT);
        AccountInfoByStateInit response = future.get();
        assertNotNull(response);
    }
}
