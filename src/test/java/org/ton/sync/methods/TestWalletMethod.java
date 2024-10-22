package org.ton.sync.methods;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.ton.schema.accounts.Accounts;
import org.ton.schema.message.MessageConsequences;
import org.ton.sync.TonapiTestBase;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.ton.utils.Constants.ACCEPT_LANGUAGE_EN;
import static org.ton.utils.Constants.ACCOUNT_ID;

public class TestWalletMethod extends TonapiTestBase {

    private static final String PUBLIC_KEY = "your_public_key_here"; // Replace with a valid public key
    private static final String XTONCONNECT_AUTH = "your_xTonconnectAuth_token_here"; // Replace with a valid token

    @Test
    @Disabled
    public void testGetBackupInfo() throws Exception {
        String response = tonapi.getWallet().getBackupInfo(XTONCONNECT_AUTH);
        assertNotNull(response);
    }

    @Test
    @Disabled
    public void testAccountVerification() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("data", "sample_data");

        String token = tonapi.getWallet().accountVerification(body);
        assertNotNull(token);
    }

    @Test
    @Disabled
    public void testGetByPublicKey() throws Exception {
        Accounts accounts = tonapi.getWallet().getByPublicKey(PUBLIC_KEY);
        assertNotNull(accounts);
        assertNotNull(accounts.getAccounts());
    }

    @Test
    public void testGetAccountSeqno() throws Exception {
        Integer seqno = tonapi.getWallet().getAccountSeqno(ACCOUNT_ID);
        assertNotNull(seqno);
    }

    @Test
    @Disabled
    public void testEmulate() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("data", "sample_data");

        MessageConsequences consequences = tonapi.getWallet().emulate(body, ACCEPT_LANGUAGE_EN);
        assertNotNull(consequences);
    }
}
