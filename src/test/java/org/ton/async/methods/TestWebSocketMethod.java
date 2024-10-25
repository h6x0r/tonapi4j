package org.ton.async.methods;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.ton.exception.TONAPIError;
import org.ton.schema.events.TraceEventData;
import org.ton.schema.events.TransactionEventData;
import org.ton.schema.events.block.BlockEventData;
import org.ton.schema.events.mempool.MempoolEventData;
import org.ton.tonapi.async.AsyncTonapi;
import org.ton.tonapi.async.methods.WebSocketMethod;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestWebSocketMethod {

    private static final String ACCOUNT_ID = "Ef8zMzMzMzMzMzMzMzMzMzMzMzMzMzMzMzMzMzMzMzMzM0vF";
    private static final List<String> ACCOUNTS_IDS = Collections.singletonList(ACCOUNT_ID);
    private static final String API_KEY = "AGAGTULGN4LNNFYAAAAD6THPXLO4G7TX7TICGHYUYW7MD7BSLFDJCYGO4VCYG3SVNFUENWI";
    private static WebSocketMethod webSocketMethod;

    @BeforeAll
    public static void setUp() throws TONAPIError {
        AsyncTonapi tonapi = new AsyncTonapi(API_KEY, false, 10);
        webSocketMethod = tonapi.getWebsocket();
    }

    @AfterAll
    public static void tearDown() {
        if (webSocketMethod != null) {
            webSocketMethod.close();
        }
    }

    @Test
    public void testSubscribeToBlocks() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        BiConsumer<BlockEventData, Object[]> handler = (event, args) -> {
            assertNotNull(event);
            System.out.println("Received BlockEventData: " + event);
            latch.countDown();
        };

        webSocketMethod.subscribeToBlocks(null, handler);

        boolean eventReceived = latch.await(60, TimeUnit.SECONDS);
        assertTrue(eventReceived, "Did not receive BlockEventData within the timeout period");
    }

    @Test
    public void testSubscribeToTransactions() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        BiConsumer<TransactionEventData, Object[]> handler = (event, args) -> {
            assertNotNull(event);
            System.out.println("Received TransactionEventData: " + event);
            latch.countDown();
        };

        webSocketMethod.subscribeToTransactions(ACCOUNTS_IDS, handler);

        boolean eventReceived = latch.await(60, TimeUnit.SECONDS);
        assertTrue(eventReceived, "Did not receive TransactionEventData within the timeout period");
    }

    @Test
    public void testSubscribeToTraces() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        BiConsumer<TraceEventData, Object[]> handler = (event, args) -> {
            assertNotNull(event);
            System.out.println("Received TraceEventData: " + event);
            latch.countDown();
        };

        webSocketMethod.subscribeToTraces(ACCOUNTS_IDS, handler);

        boolean eventReceived = latch.await(60, TimeUnit.SECONDS);
        assertTrue(eventReceived, "Did not receive TraceEventData within the timeout period");
    }

    @Test
    public void testSubscribeToMempool() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        BiConsumer<MempoolEventData, Object[]> handler = (event, args) -> {
            assertNotNull(event);
            System.out.println("Received MempoolEventData: " + event);
            latch.countDown();
        };

        webSocketMethod.subscribeToMempool(ACCOUNTS_IDS, handler);

        boolean eventReceived = latch.await(60, TimeUnit.SECONDS);
        assertTrue(eventReceived, "Did not receive MempoolEventData within the timeout period");
    }

    @Test
    public void testMultipleSubscriptions() throws Exception {
        CountDownLatch latch = new CountDownLatch(2);

        BiConsumer<TransactionEventData, Object[]> handler1 = (event, args) -> {
            assertNotNull(event);
            System.out.println("Handler1 received TransactionEventData: " + event);
            latch.countDown();
        };

        BiConsumer<TransactionEventData, Object[]> handler2 = (event, args) -> {
            assertNotNull(event);
            System.out.println("Handler2 received TransactionEventData: " + event);
            latch.countDown();
        };

        webSocketMethod.subscribeToTransactions(ACCOUNTS_IDS, handler1);
        webSocketMethod.subscribeToTransactions(ACCOUNTS_IDS, handler2);

        boolean eventReceived = latch.await(120, TimeUnit.SECONDS);
        assertTrue(eventReceived, "Did not receive all TransactionEventData within the timeout period");
    }
}
