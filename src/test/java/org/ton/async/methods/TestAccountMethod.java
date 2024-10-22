package org.ton.async.methods;

import org.junit.jupiter.api.Test;
import org.ton.async.AsyncTonapiTestBase;
import org.ton.schema.accounts.Account;
import org.ton.schema.accounts.Accounts;
import org.ton.schema.accounts.BalanceChange;
import org.ton.schema.accounts.DnsExpiring;
import org.ton.schema.accounts.FoundAccounts;
import org.ton.schema.accounts.PublicKey;
import org.ton.schema.accounts.Subscriptions;
import org.ton.schema.domain.DomainNames;
import org.ton.schema.events.AccountEvent;
import org.ton.schema.events.AccountEvents;
import org.ton.schema.jettons.JettonBalance;
import org.ton.schema.jettons.JettonsBalances;
import org.ton.schema.nft.NftItems;
import org.ton.schema.traces.TraceIds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.ton.utils.Constants.ACCEPT_LANGUAGE_EN;
import static org.ton.utils.Constants.ACCOUNT_ID;
import static org.ton.utils.Constants.BEFORE_LT;
import static org.ton.utils.Constants.COLLECTION_ADDRESS;
import static org.ton.utils.Constants.DOMAIN_NAME;
import static org.ton.utils.Constants.END_DATE;
import static org.ton.utils.Constants.EVENT_ID;
import static org.ton.utils.Constants.JETTON_ID;
import static org.ton.utils.Constants.LARGE_LIMIT;
import static org.ton.utils.Constants.LIMIT;
import static org.ton.utils.Constants.OFFSET;
import static org.ton.utils.Constants.START_DATE;

public class TestAccountMethod extends AsyncTonapiTestBase {

    @Test
    public void testGetInfo() throws Exception {
        CompletableFuture<Account> future = tonapi.getAccounts().getInfo(ACCOUNT_ID);
        Account response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetBulkInfo() throws Exception {
        CompletableFuture<Accounts> future = tonapi.getAccounts().getBulkInfo(Collections.singletonList(ACCOUNT_ID));
        Accounts response = future.get();
        assertNotNull(response);

        for (var a : response.getAccounts()) {
            System.out.println("Account address: " + a.getAddress());
            System.out.println("Account address (userfriendly): " + a.getAddress().toUserFriendly(false));
            System.out.println("Account address (userfriendly and bounceable): " + a.getAddress().toUserFriendly(true));
            System.out.println("Account address (raw): " + a.getAddress().toRaw());
            System.out.println("Account balance: " + a.getBalance());
            System.out.println("Account balance (nanoton): " + a.getBalance().toNano(9));
            System.out.println("Account balance (amount and precision 2): " + a.getBalance().toAmount(9, 2));
            System.out.println("Account balance (amount and precision 3): " + a.getBalance().toAmount(9, 3));
            System.out.println("Account balance (amount and precision 4): " + a.getBalance().toAmount(9, 4));
        }
    }

    @Test
    public void testGetDomains() throws Exception {
        CompletableFuture<DomainNames> future = tonapi.getAccounts().getDomains(ACCOUNT_ID);
        DomainNames response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetJettons() throws Exception {
        CompletableFuture<JettonsBalances> future = tonapi.getAccounts().getJettonsBalances(ACCOUNT_ID, null, null);
        JettonsBalances response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetJetton() throws Exception {
        List<String> currencies = new ArrayList<>();
        List<String> supportedExtensions = new ArrayList<>();

        CompletableFuture<JettonBalance> future = tonapi.getAccounts().getJettonBalance(ACCOUNT_ID, JETTON_ID, currencies, supportedExtensions);
        JettonBalance response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetJettonsHistory() throws Exception {
        CompletableFuture<AccountEvents> future = tonapi.getAccounts().getJettonsHistory(ACCOUNT_ID, LIMIT, null, ACCEPT_LANGUAGE_EN, null, null);
        AccountEvents response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetJettonsHistoryByJetton() throws Exception {
        CompletableFuture<AccountEvents> future = tonapi.getAccounts().getJettonsHistoryByJetton(ACCOUNT_ID, JETTON_ID, LIMIT, null, ACCEPT_LANGUAGE_EN, null, null);
        AccountEvents response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetNfts() throws Exception {
        var collection = "";

        CompletableFuture<NftItems> future = tonapi.getAccounts().getNfts(ACCOUNT_ID, LIMIT, OFFSET, collection, false);
        NftItems response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetAllNfts() throws Exception {
        CompletableFuture<NftItems> future = tonapi.getAccounts().getAllNfts(ACCOUNT_ID, COLLECTION_ADDRESS, false, LARGE_LIMIT);
        NftItems response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetTraces() throws Exception {
        CompletableFuture<TraceIds> future = tonapi.getAccounts().getTraces(ACCOUNT_ID, LIMIT);
        TraceIds response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetEvent() throws Exception {
        CompletableFuture<AccountEvent> future = tonapi.getAccounts().getEvent(ACCOUNT_ID, EVENT_ID, ACCEPT_LANGUAGE_EN, false);
        AccountEvent response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetEvents() throws Exception {
        CompletableFuture<AccountEvents> future = tonapi.getAccounts().getEvents(ACCOUNT_ID, LIMIT, BEFORE_LT, ACCEPT_LANGUAGE_EN, false, false, START_DATE, END_DATE);
        AccountEvents response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetNftHistory() throws Exception {
        CompletableFuture<AccountEvents> future = tonapi.getAccounts().getNftHistory(ACCOUNT_ID, LIMIT, null, ACCEPT_LANGUAGE_EN, null, null);
        AccountEvents response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testSearchByDomain() throws Exception {
        CompletableFuture<FoundAccounts> future = tonapi.getAccounts().searchByDomain(DOMAIN_NAME);
        FoundAccounts response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetSubscriptions() throws Exception {
        CompletableFuture<Subscriptions> future = tonapi.getAccounts().getSubscriptions(ACCOUNT_ID);
        Subscriptions response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetExpiringDns() throws Exception {
        CompletableFuture<DnsExpiring> future = tonapi.getAccounts().getExpiringDns(ACCOUNT_ID, null);
        DnsExpiring response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetPublicKey() throws Exception {
        CompletableFuture<PublicKey> future = tonapi.getAccounts().getPublicKey(ACCOUNT_ID);
        PublicKey response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetBalanceChange() throws Exception {
        CompletableFuture<BalanceChange> future = tonapi.getAccounts().getBalanceChange(ACCOUNT_ID, START_DATE, END_DATE);
        BalanceChange response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testReindex() throws Exception {
        CompletableFuture<Void> future = tonapi.getAccounts().reindex(ACCOUNT_ID);
        assertNull(future.get()); // returns null in case return type is void
    }
}
