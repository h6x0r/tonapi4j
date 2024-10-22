package org.ton.sync.methods;

import org.junit.jupiter.api.Test;
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
import org.ton.sync.TonapiTestBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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

public class TestAccountMethod extends TonapiTestBase {

    @Test
    public void testGetInfo() {
        Account response = tonapi.getAccounts().getInfo(ACCOUNT_ID);
        assertNotNull(response);
    }

    @Test
    public void testGetBulkInfo() {
        Accounts response = tonapi.getAccounts().getBulkInfo(Collections.singletonList(ACCOUNT_ID));
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
    public void testGetDomains() {
        DomainNames response = tonapi.getAccounts().getDomains(ACCOUNT_ID);
        assertNotNull(response);
    }

    @Test
    public void testGetJettons() {
        JettonsBalances response = tonapi.getAccounts().getJettonsBalances(ACCOUNT_ID, null, null);
        assertNotNull(response);
    }

    @Test
    public void testGetJetton() {
        List<String> currencies = new ArrayList<>();
        List<String> supportedExtensions = new ArrayList<>();

        JettonBalance response = tonapi.getAccounts().getJettonBalance(ACCOUNT_ID, JETTON_ID, currencies, supportedExtensions);
        assertNotNull(response);
    }

    @Test
    public void testGetJettonsHistory() {
        AccountEvents response = tonapi.getAccounts().getJettonsHistory(ACCOUNT_ID, LIMIT, null, ACCEPT_LANGUAGE_EN, null, null);
        assertNotNull(response);
    }

    @Test
    public void testGetJettonsHistoryByJetton() {
        AccountEvents response = tonapi.getAccounts().getJettonsHistoryByJetton(ACCOUNT_ID, JETTON_ID, LIMIT, null, ACCEPT_LANGUAGE_EN, null, null);
        assertNotNull(response);
    }

    @Test
    public void testGetNfts() {
        var collection = "";

        NftItems response = tonapi.getAccounts().getNfts(ACCOUNT_ID, LIMIT, OFFSET, collection, false);
        assertNotNull(response);
    }

    @Test
    public void testGetAllNfts() {
        NftItems response = tonapi.getAccounts().getAllNfts(ACCOUNT_ID, COLLECTION_ADDRESS, false, LARGE_LIMIT);
        assertNotNull(response);
    }

    @Test
    public void testGetTraces() {
        TraceIds response = tonapi.getAccounts().getTraces(ACCOUNT_ID, LIMIT);
        assertNotNull(response);
    }

    @Test
    public void testGetEvent() {
        AccountEvent response = tonapi.getAccounts().getEvent(ACCOUNT_ID, EVENT_ID, ACCEPT_LANGUAGE_EN, false);
        assertNotNull(response);
    }

    @Test
    public void testGetEvents() {
        AccountEvents response = tonapi.getAccounts().getEvents(ACCOUNT_ID, LIMIT, BEFORE_LT, ACCEPT_LANGUAGE_EN, false, false, START_DATE, END_DATE);
        assertNotNull(response);
    }

    @Test
    public void testGetNftHistory() {
        AccountEvents response = tonapi.getAccounts().getNftHistory(ACCOUNT_ID, LIMIT, null, ACCEPT_LANGUAGE_EN, null, null);
        assertNotNull(response);
    }

    @Test
    public void testSearchByDomain() {
        FoundAccounts response = tonapi.getAccounts().searchByDomain(DOMAIN_NAME);
        assertNotNull(response);
    }

    @Test
    public void testGetSubscriptions() {
        Subscriptions response = tonapi.getAccounts().getSubscriptions(ACCOUNT_ID);
        assertNotNull(response);
    }

    @Test
    public void testGetExpiringDns() {
        DnsExpiring response = tonapi.getAccounts().getExpiringDns(ACCOUNT_ID, null);
        assertNotNull(response);
    }

    @Test
    public void testGetPublicKey() {
        PublicKey response = tonapi.getAccounts().getPublicKey(ACCOUNT_ID);
        assertNotNull(response);
    }

    @Test
    public void testGetBalanceChange() {
        BalanceChange response = tonapi.getAccounts().getBalanceChange(ACCOUNT_ID, START_DATE, END_DATE);
        assertNotNull(response);
    }

    @Test
    public void testReindex() {
        tonapi.getAccounts().reindex(ACCOUNT_ID);
    }
}
