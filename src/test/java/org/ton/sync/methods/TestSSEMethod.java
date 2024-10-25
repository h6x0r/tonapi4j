package org.ton.sync.methods;

import org.junit.jupiter.api.Test;
import org.ton.schema.events.TraceEventData;
import org.ton.schema.events.TransactionEventData;
import org.ton.schema.events.block.BlockEventData;
import org.ton.schema.events.mempool.MempoolEventData;
import org.ton.sync.TonapiTestBase;
import org.ton.tonapi.sync.methods.SSEMethod;

import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class TestSSEMethod extends TonapiTestBase {

    private static final String ACCOUNT_ID = "EQChB2eMoFG4ThuEsZ6ehlBPKJXOjNxlR5B7qKZNGIv256Da";
    private final SSEMethod sseMethod = tonapi.getSse();

    @Test
    public void testSubscribeToTransactions() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        sseMethod.subscribeToTransactions(
                (TransactionEventData eventData) -> {
                    assertNotNull(eventData);
                    System.out.println("Received TransactionEventData: " + eventData);
                    latch.countDown();
                },
                Collections.singletonList("ALL"),
                null
        );

        boolean received = latch.await(60, TimeUnit.SECONDS);
        assertTrue(received, "Did not receive TransactionEventData within the timeout period");
    }

    @Test
    public void testSubscribeToTraces() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        sseMethod.subscribeToTraces(
                (TraceEventData eventData) -> {
                    assertNotNull(eventData);
                    System.out.println("Received TraceEventData: " + eventData);
                    latch.countDown();
                },
                Collections.singletonList("ALL")
        );

        boolean received = latch.await(60, TimeUnit.SECONDS);
        assertTrue(received, "Did not receive TraceEventData within the timeout period");
    }

    @Test
    public void testSubscribeToMempool() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        sseMethod.subscribeToMempool(
                (MempoolEventData eventData) -> {
                    assertNotNull(eventData);
                    System.out.println("Received MempoolEventData: " + eventData);
                    latch.countDown();
                },
                Collections.singletonList(ACCOUNT_ID)
        );

        boolean received = latch.await(60, TimeUnit.SECONDS);
        assertTrue(received, "Did not receive MempoolEventData within the timeout period");
    }

    @Test
    public void testSubscribeToBlocks() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        sseMethod.subscribeToBlocks(
                (BlockEventData eventData) -> {
                    assertNotNull(eventData);
                    System.out.println("Received BlockEventData: " + eventData);
                    latch.countDown();
                },
                null // Subscribe to all workchains
        );

        boolean received = latch.await(60, TimeUnit.SECONDS);
        assertTrue(received, "Did not receive BlockEventData within the timeout period");
    }
}
