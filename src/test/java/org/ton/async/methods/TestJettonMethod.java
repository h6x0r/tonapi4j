package org.ton.async.methods;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.ton.utils.Constants.LIMIT;
import static org.ton.utils.Constants.OFFSET;

import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.ton.async.AsyncTonapiTestBase;
import org.ton.schema.events.Event;
import org.ton.schema.jettons.JettonHolders;
import org.ton.schema.jettons.JettonInfo;
import org.ton.schema.jettons.JettonTransferPayload;
import org.ton.schema.jettons.Jettons;

public class TestJettonMethod extends AsyncTonapiTestBase {

  private static final String ACCOUNT_ID = "EQBCFwW8uFUh-amdRmNY9NyeDEaeDYXd9ggJGsicpqVcHq7B";
  private static final String EVENT_ID = "68656e74d18b10309e41e057191abcfc42f973c82bc84326985cdbf7bf89b126";

  @Test
  public void testGetInfo() throws Exception {
    CompletableFuture<JettonInfo> future = tonapi.getJettons().getInfo(ACCOUNT_ID);
    JettonInfo response = future.get();
    assertNotNull(response);
  }

  @Test
  public void testGetHolders() throws Exception {
    CompletableFuture<JettonHolders> future = tonapi.getJettons()
        .getHolders(ACCOUNT_ID, LIMIT, OFFSET);
    JettonHolders response = future.get();
    assertNotNull(response);
  }

  @Test
  public void testGetAllHolders() throws Exception {
    CompletableFuture<JettonHolders> future = tonapi.getJettons().getAllHolders(ACCOUNT_ID);
    JettonHolders response = future.get();
    assertNotNull(response);
  }

  @Test
  public void testGetAllJettons() throws Exception {
    CompletableFuture<Jettons> future = tonapi.getJettons().getAllJettons(LIMIT, OFFSET);
    Jettons response = future.get();
    assertNotNull(response);
  }

  @Test
  public void testGetJettonTransferEvent() throws Exception {
    CompletableFuture<Event> future = tonapi.getJettons().getJettonTransferEvent(EVENT_ID);
    Event response = future.get();
    assertNotNull(response);
  }

  @Test
  @Disabled
  public void testGetJettonTransferPayload() throws Exception {
    CompletableFuture<JettonTransferPayload> future = tonapi.getJettons()
        .getJettonTransferPayload(ACCOUNT_ID, ACCOUNT_ID);
    JettonTransferPayload response = future.get();
    assertNotNull(response);
  }
}
