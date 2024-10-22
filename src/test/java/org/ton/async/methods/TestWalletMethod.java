package org.ton.async.methods;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.ton.async.AsyncTonapiTestBase;
import org.ton.schema.accounts.Accounts;
import org.ton.schema.message.MessageConsequences;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.ton.utils.Constants.ACCEPT_LANGUAGE_EN;
import static org.ton.utils.Constants.ACCOUNT_ID;

public class TestWalletMethod extends AsyncTonapiTestBase {

    private static final String PUBLIC_KEY = "your_public_key_here"; // Replace with a valid public key
    private static final String XTONCONNECT_AUTH = "your_xTonconnectAuth_token_here"; // Replace with a valid token

    @Test
    @Disabled
    public void testGetBackupInfo() throws Exception {
        CompletableFuture<String> future = tonapi.getWallet().getBackupInfo(XTONCONNECT_AUTH);
        String response = future.get();

        assertNotNull(response);
    }

    @Test
    @Disabled
    public void testAccountVerification() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("data", "sample_data");

        CompletableFuture<String> future = tonapi.getWallet().accountVerification(body);
        String token = future.get();

        assertNotNull(token);
    }

    @Test
    @Disabled
    public void testGetByPublicKey() throws Exception {
        CompletableFuture<Accounts> future = tonapi.getWallet().getByPublicKey(PUBLIC_KEY);
        Accounts accounts = future.get();

        assertNotNull(accounts);
        assertNotNull(accounts.getAccounts());
    }

    @Test
    public void testGetAccountSeqno() throws Exception {
        CompletableFuture<Integer> future = tonapi.getWallet().getAccountSeqno(ACCOUNT_ID);
        Integer seqno = future.get();

        assertNotNull(seqno);
    }

    @Test
    @Disabled
    public void testEmulate() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("data", "sample_data");

        CompletableFuture<MessageConsequences> future = tonapi.getWallet().emulate(body, ACCEPT_LANGUAGE_EN);
        MessageConsequences consequences = future.get();

        assertNotNull(consequences);
    }
}
