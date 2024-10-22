package org.ton.sync.methods;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.ton.schema.events.AccountEvents;
import org.ton.schema.inscriptions.InscriptionBalances;
import org.ton.sync.TonapiTestBase;
import org.ton.util.Utils;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.ton.utils.Constants.ACCEPT_LANGUAGE_EN;
import static org.ton.utils.Constants.ACCOUNT_ID;
import static org.ton.utils.Constants.AMOUNT;
import static org.ton.utils.Constants.BEFORE_LT;
import static org.ton.utils.Constants.LIMIT;
import static org.ton.utils.Constants.OFFSET;
import static org.ton.utils.Constants.TICKER;

public class TestInscriptionsMethod extends TonapiTestBase {

    @Test
    public void testGetAllInscriptions() {
        InscriptionBalances response = tonapi.getInscriptions().getAllInscriptions(ACCOUNT_ID, LIMIT, OFFSET);
        assertNotNull(response);
    }

    @Test
    @Disabled
    public void testGetInscriptionHistory() {
        AccountEvents response = tonapi.getInscriptions().getInscriptionHistory(ACCOUNT_ID, BEFORE_LT, LIMIT, ACCEPT_LANGUAGE_EN);
        assertNotNull(response);
    }

    @Test
    @Disabled
    public void testGetInscriptionHistoryByTicker() {
        AccountEvents response = tonapi.getInscriptions().getInscriptionHistoryByTicker(ACCOUNT_ID, TICKER, BEFORE_LT, LIMIT, ACCEPT_LANGUAGE_EN);
        assertNotNull(response);
    }

    @Test
    public void testCreateInscriptionComments() {
        Map<String, String> response = tonapi.getInscriptions().createInscriptionComment(
                ACCOUNT_ID,
                Utils.toNano(AMOUNT, 9),
                null,
                ACCOUNT_ID,
                null,
                null,
                null
        );
        assertNotNull(response);
    }
}
