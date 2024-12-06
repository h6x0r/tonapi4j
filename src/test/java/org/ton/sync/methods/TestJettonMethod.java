package org.ton.sync.methods;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.ton.utils.Constants.LIMIT;
import static org.ton.utils.Constants.OFFSET;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.ton.schema.events.Event;
import org.ton.schema.jettons.JettonHolders;
import org.ton.schema.jettons.JettonInfo;
import org.ton.schema.jettons.JettonTransferPayload;
import org.ton.schema.jettons.Jettons;
import org.ton.sync.TonapiTestBase;

public class TestJettonMethod extends TonapiTestBase {

  private static final String ACCOUNT_ID = "EQBCFwW8uFUh-amdRmNY9NyeDEaeDYXd9ggJGsicpqVcHq7B";
  private static final String JETTON_ID_2 = "EQCqR2lSC_156MaxekFTW1qOAxnR1MBVzAlvJwwPPklt_l-n";
  private static final String EVENT_ID = "68656e74d18b10309e41e057191abcfc42f973c82bc84326985cdbf7bf89b126";

  @Test
  public void testGetInfo() {
    JettonInfo response = tonapi.getJettons().getInfo(ACCOUNT_ID);
    assertNotNull(response);
  }

  @Test
  public void testGetHolders() {
    JettonHolders response = tonapi.getJettons().getHolders(ACCOUNT_ID, LIMIT, OFFSET);
    assertNotNull(response);
  }

  @Test
  public void testGetAllHolders() {
    JettonHolders response = tonapi.getJettons().getAllHolders(ACCOUNT_ID);
    assertNotNull(response);
  }

  @Test
  public void testGetAllJettons() {
    Jettons response = tonapi.getJettons().getAllJettons(LIMIT, OFFSET);
    assertNotNull(response);
  }

  @Test
  public void testGetJettonTransferEvent() {
    Event response = tonapi.getJettons().getJettonTransferEvent(EVENT_ID);
    assertNotNull(response);
  }

  @Test
  @Disabled
  public void testGetJettonTransferPayload() {
    JettonTransferPayload response = tonapi.getJettons()
        .getJettonTransferPayload(JETTON_ID_2, ACCOUNT_ID);
    assertNotNull(response);
  }
}
