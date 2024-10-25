package org.ton.async.methods;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.ton.utils.Constants.ACCEPT_LANGUAGE_EN;
import static org.ton.utils.Constants.ACCOUNT_ID;
import static org.ton.utils.Constants.AMOUNT;
import static org.ton.utils.Constants.BEFORE_LT;
import static org.ton.utils.Constants.LIMIT;
import static org.ton.utils.Constants.OFFSET;
import static org.ton.utils.Constants.TICKER;

import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.ton.async.AsyncTonapiTestBase;
import org.ton.schema.events.AccountEvents;
import org.ton.schema.inscriptions.InscriptionBalances;
import org.ton.schema.inscriptions.InscriptionCommentResponse;
import org.ton.util.Utils;

public class TestInscriptionsMethod extends AsyncTonapiTestBase {

  @Test
  public void testGetAllInscriptions() throws Exception {
    CompletableFuture<InscriptionBalances> future = tonapi.getInscriptions()
        .getAllInscriptions(ACCOUNT_ID, LIMIT, OFFSET);
    InscriptionBalances response = future.get();
    assertNotNull(response);
  }

  @Test
  @Disabled
  public void testGetInscriptionHistory() throws Exception {
    CompletableFuture<AccountEvents> future = tonapi.getInscriptions()
        .getInscriptionHistory(ACCOUNT_ID, BEFORE_LT, LIMIT, ACCEPT_LANGUAGE_EN);
    AccountEvents response = future.get();
    assertNotNull(response);
  }

  @Test
  @Disabled
  public void testGetInscriptionHistoryByTicker() throws Exception {
    CompletableFuture<AccountEvents> future = tonapi.getInscriptions()
        .getInscriptionHistoryByTicker(ACCOUNT_ID, TICKER, BEFORE_LT, LIMIT, ACCEPT_LANGUAGE_EN);
    AccountEvents response = future.get();
    assertNotNull(response);
  }

  @Test
  public void testCreateInscriptionComments() throws Exception {
    CompletableFuture<InscriptionCommentResponse> future = tonapi.getInscriptions()
        .createInscriptionComment(
            ACCOUNT_ID,
            Utils.toNano(AMOUNT, 9),
            null,
            ACCOUNT_ID,
            null,
            null,
            null
        );
    InscriptionCommentResponse response = future.get();
    assertNotNull(response);
  }
}
