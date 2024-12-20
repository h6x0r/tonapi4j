package org.ton.async.methods;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.ton.async.AsyncTonapiTestBase;

public class TestSSEMethod extends AsyncTonapiTestBase {

  private static final String ACCOUNT_ID = "EQChB2eMoFG4ThuEsZ6ehlBPKJXOjNxlR5B7qKZNGIv256Da";
  private static final List<String> ACCOUNTS_IDS = Collections.singletonList("ALL");

  @Test
  public void testSubscribeToTransactions() throws Exception {
    CountDownLatch latch = new CountDownLatch(1);

    tonapi.getSse().subscribeToTransactions(
        eventData -> {
          assertNotNull(eventData);
          latch.countDown();
        },
        ACCOUNTS_IDS,
        null
    );

    boolean received = latch.await(30, TimeUnit.SECONDS);
    assertTrue(received);
  }

  @Test
  public void testSubscribeToTraces() throws Exception {
    CountDownLatch latch = new CountDownLatch(1);

    tonapi.getSse().subscribeToTraces(
        eventData -> {
          assertNotNull(eventData);
          latch.countDown();
        },
        ACCOUNTS_IDS
    );

    boolean received = latch.await(30, TimeUnit.SECONDS);
    assertTrue(received);
  }

  @Test
  public void testSubscribeToMempool() throws Exception {
    CountDownLatch latch = new CountDownLatch(1);

    tonapi.getSse().subscribeToMempool(
        eventData -> {
          assertNotNull(eventData);
          latch.countDown();
        },
        Collections.singletonList(ACCOUNT_ID)
    );

    boolean received = latch.await(30, TimeUnit.SECONDS);
    assertTrue(received);
  }

  @Test
  public void testSubscribeToBlocks() throws Exception {
    CountDownLatch latch = new CountDownLatch(1);

    tonapi.getSse().subscribeToBlocks(
        eventData -> {
          assertNotNull(eventData);
          latch.countDown();
        },
        null
    );

    boolean received = latch.await(30, TimeUnit.SECONDS);
    assertTrue(received);
  }
}
