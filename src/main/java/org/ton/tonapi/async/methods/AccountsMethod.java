package org.ton.tonapi.async.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import org.ton.exception.TONAPIError;
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
import org.ton.schema.multisig.Multisigs;
import org.ton.schema.nft.NftItem;
import org.ton.schema.nft.NftItems;
import org.ton.schema.traces.TraceIds;
import org.ton.tonapi.async.AsyncTonapiClientBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class AccountsMethod extends AsyncTonapiClientBase {

    public AccountsMethod(AsyncTonapiClientBase client) {
        super(client);
    }

    /**
     * Get information about multiple accounts without low-level details.
     *
     * @param accountIds List of wallet addresses
     * @return CompletableFuture of Accounts object containing the list of accounts
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<Accounts> getBulkInfo(List<String> accountIds) throws TONAPIError {
        String method = "v2/accounts/_bulk";
        Map<String, Object> body = new HashMap<>();
        body.put("account_ids", accountIds);

        return this.post(method, null, body, null, new TypeReference<>() {
        });
    }

    /**
     * Get human-friendly information about an account without low-level details.
     *
     * @param accountId Account ID
     * @return CompletableFuture of Account object containing account details
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<Account> getInfo(String accountId) throws TONAPIError {
        String method = String.format("v2/accounts/%s", accountId);
        return this.get(method, null, null, new TypeReference<>() {
        });
    }

    /**
     * Get domains associated with a wallet account.
     *
     * @param accountId Account ID
     * @return CompletableFuture of DomainNames object containing associated domains
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<DomainNames> getDomains(String accountId) throws TONAPIError {
        String method = String.format("v2/accounts/%s/dns/backresolve", accountId);
        return this.get(method, null, null, new TypeReference<>() {
        });
    }

    /**
     * Get all Jetton balances by the owner's address.
     *
     * @param accountId           Account ID
     * @param currencies          Optional list of currencies
     * @param supportedExtensions Optional list of supported extensions
     * @return CompletableFuture of JettonsBalances object containing Jetton balances
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<JettonsBalances> getJettonsBalances(
            String accountId,
            List<String> currencies,
            List<String> supportedExtensions) throws TONAPIError {
        String method = String.format("v2/accounts/%s/jettons", accountId);
        Map<String, Object> params = new HashMap<>();
        if (supportedExtensions != null && !supportedExtensions.isEmpty()) {
            params.put("supported_extensions", String.join(",", supportedExtensions));
        }
        if (currencies != null && !currencies.isEmpty()) {
            params.put("currencies", String.join(",", currencies));
        }
        return this.get(method, params, null, new TypeReference<>() {
        });
    }

    /**
     * Get Jetton balance by the owner's address.
     *
     * @param accountId           Account ID
     * @param jettonId            jetton master address
     * @param currencies          Optional list of currencies
     * @param supportedExtensions Optional list of supported extensions
     * @return CompletableFuture of JettonBalance object containing the Jetton balance
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<JettonBalance> getJettonBalance(
            String accountId,
            String jettonId,
            List<String> currencies,
            List<String> supportedExtensions) throws TONAPIError {
        String method = String.format("v2/accounts/%s/jettons/%s", accountId, jettonId);
        Map<String, Object> params = new HashMap<>();
        if (supportedExtensions != null && !supportedExtensions.isEmpty()) {
            params.put("supported_extensions", String.join(",", supportedExtensions));
        }
        if (currencies != null && !currencies.isEmpty()) {
            params.put("currencies", String.join(",", currencies));
        }
        return this.get(method, params, null, new TypeReference<>() {
        });
    }

    /**
     * Get the Jetton transfer history for an account.
     *
     * @param accountId      Account ID
     * @param limit          Number of records to return
     * @param beforeLt       Optional parameter to get events before the specified logical time (lt)
     * @param acceptLanguage Accept-Language header value. Default is "en". Example -> ru-RU,ru;q=0.5
     * @param startDate      Optional start date (timestamp)
     * @param endDate        Optional end date (timestamp)
     * @return CompletableFuture of AccountEvents object containing the event history
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<AccountEvents> getJettonsHistory(
            String accountId,
            int limit,
            Long beforeLt,
            String acceptLanguage,
            Long startDate,
            Long endDate) throws TONAPIError {
        String method = String.format("v2/accounts/%s/jettons/history", accountId);
        Map<String, Object> params = new HashMap<>();
        params.put("limit", limit);
        if (beforeLt != null) {
            params.put("before_lt", beforeLt);
        }
        if (startDate != null) {
            params.put("start_date", startDate);
        }
        if (endDate != null) {
            params.put("end_date", endDate);
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept-Language", acceptLanguage);
        return this.get(method, params, headers, new TypeReference<>() {
        });
    }

    /**
     * Get the Jetton transfer history for an account and a specific Jetton.
     *
     * @param accountId      Account ID
     * @param jettonId       jetton master address
     * @param limit          Number of records to return
     * @param beforeLt       Optional parameter to get events before the specified logical time (lt)
     * @param acceptLanguage Accept-Language header value. Default is "en". Example -> ru-RU,ru;q=0.5
     * @param startDate      Optional start date (timestamp)
     * @param endDate        Optional end date (timestamp)
     * @return CompletableFuture of AccountEvents object containing the event history
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<AccountEvents> getJettonsHistoryByJetton(
            String accountId,
            String jettonId,
            int limit,
            Long beforeLt,
            String acceptLanguage,
            Long startDate,
            Long endDate) throws TONAPIError {
        String method = String.format("v2/accounts/%s/jettons/%s/history", accountId, jettonId);
        Map<String, Object> params = new HashMap<>();
        params.put("limit", limit);
        if (beforeLt != null) {
            params.put("before_lt", beforeLt);
        }
        if (startDate != null) {
            params.put("start_date", startDate);
        }
        if (endDate != null) {
            params.put("end_date", endDate);
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept-Language", acceptLanguage);
        return this.get(method, params, headers, new TypeReference<>() {
        });
    }

    /**
     * Get NFT items by the owner's address.
     *
     * @param accountId         Account ID
     * @param limit             Number of records to return
     * @param offset            Offset for pagination
     * @param collection        Optional filter by collection address
     * @param indirectOwnership Include items not owned directly
     * @return CompletableFuture of NftItems object containing the list of NFT items
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<NftItems> getNfts(
            String accountId,
            int limit,
            int offset,
            String collection,
            boolean indirectOwnership) throws TONAPIError {
        String method = String.format("v2/accounts/%s/nfts", accountId);
        Map<String, Object> params = new HashMap<>();
        params.put("limit", limit);
        params.put("offset", offset);
        params.put("indirect_ownership", indirectOwnership);
        if (collection != null && !collection.isEmpty()) {
            params.put("collection", collection);
        }
        return this.get(method, params, null, new TypeReference<>() {
        });
    }

    /**
     * Get all NFT items by the owner's address.
     *
     * @param accountId         Account ID
     * @param collection        Optional filter by collection address
     * @param indirectOwnership Include items not owned directly
     * @return CompletableFuture of NftItems object containing all NFT items
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<NftItems> getAllNfts(
            String accountId,
            String collection,
            boolean indirectOwnership,
            int limit) throws TONAPIError {
        List<NftItem> nftItems = new ArrayList<>();
        AtomicInteger offset = new AtomicInteger(0);

        CompletableFuture<NftItems> future = new CompletableFuture<>();

        fetchNfts(accountId, collection, indirectOwnership, limit, offset, nftItems, future);

        return future;
    }

    private void fetchNfts(String accountId, String collection, boolean indirectOwnership,
                           int limit, AtomicInteger offset, List<NftItem> nftItems, CompletableFuture<NftItems> future) {
        this.getNfts(accountId, limit, offset.get(), collection, indirectOwnership)
                .thenAccept(result -> {
                    nftItems.addAll(result.getNftItems());
                    if (result.getNftItems().isEmpty() || result.getNftItems().size() < limit) {
                        future.complete(NftItems.builder().nftItems(nftItems).build());
                    } else {
                        offset.addAndGet(limit);
                        fetchNfts(accountId, collection, indirectOwnership, limit, offset, nftItems, future);
                    }
                })
                .exceptionally(ex -> {
                    future.completeExceptionally(ex);
                    return null;
                });
    }

    /**
     * Get events for an account.
     *
     * @param accountId      Account ID
     * @param limit          Number of records to return
     * @param beforeLt       Optional parameter to get events before the specified logical time (lt)
     * @param acceptLanguage Accept-Language header value. Default is "en". Example -> ru-RU,ru;q=0.5
     * @param initiator      Show only events initiated by this account
     * @param subjectOnly    Filter actions where the account is not the real subject
     * @param startDate      Optional start date (timestamp)
     * @param endDate        Optional end date (timestamp)
     * @return CompletableFuture of AccountEvents object containing the events
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<AccountEvents> getEvents(
            String accountId,
            int limit,
            Long beforeLt,
            String acceptLanguage,
            boolean initiator,
            boolean subjectOnly,
            Long startDate,
            Long endDate) throws TONAPIError {
        String method = String.format("v2/accounts/%s/events", accountId);
        Map<String, Object> params = new HashMap<>();
        params.put("limit", limit);
        params.put("initiator", initiator);
        params.put("subject_only", subjectOnly);
        if (beforeLt != null) {
            params.put("before_lt", beforeLt);
        }
        if (startDate != null) {
            params.put("start_date", startDate);
        }
        if (endDate != null) {
            params.put("end_date", endDate);
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept-Language", acceptLanguage);
        return this.get(method, params, headers, new TypeReference<>() {
        });
    }

    /**
     * Get a specific event for an account by event ID.
     *
     * @param accountId      Account ID
     * @param eventId        Event ID
     * @param acceptLanguage Accept-Language header value. Default is "en". Example -> ru-RU,ru;q=0.5
     * @param subjectOnly    Filter actions where the account is not the real subject
     * @return CompletableFuture of AccountEvent object containing the event details
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<AccountEvent> getEvent(
            String accountId,
            String eventId,
            String acceptLanguage,
            boolean subjectOnly) throws TONAPIError {
        String method = String.format("v2/accounts/%s/events/%s", accountId, eventId);
        Map<String, Object> params = new HashMap<>();
        if (subjectOnly) {
            params.put("subject_only", true);
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept-Language", acceptLanguage);
        return this.get(method, params, headers, new TypeReference<>() {
        });
    }

    /**
     * Get traces for an account.
     *
     * @param accountId Account ID
     * @param limit     Number of records to return
     * @return CompletableFuture of TraceIds object containing trace IDs
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<TraceIds> getTraces(String accountId, int limit) throws TONAPIError {
        String method = String.format("v2/accounts/%s/traces", accountId);
        Map<String, Object> params = new HashMap<>();
        params.put("limit", limit);
        return this.get(method, params, null, new TypeReference<>() {
        });
    }

    /**
     * Get the NFT transfer history.
     *
     * @param accountId      Account ID
     * @param limit          Number of records to return
     * @param beforeLt       Optional parameter to get events before the specified logical time (lt)
     * @param acceptLanguage Accept-Language header value. Default is "en". Example -> ru-RU,ru;q=0.5
     * @param startDate      Optional start date (timestamp)
     * @param endDate        Optional end date (timestamp)
     * @return CompletableFuture of AccountEvents object containing the NFT event history
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<AccountEvents> getNftHistory(
            String accountId,
            int limit,
            Long beforeLt,
            String acceptLanguage,
            Long startDate,
            Long endDate) throws TONAPIError {
        String method = String.format("v2/accounts/%s/nfts/history", accountId);
        Map<String, Object> params = new HashMap<>();
        params.put("limit", limit);
        if (beforeLt != null) {
            params.put("before_lt", beforeLt);
        }
        if (startDate != null) {
            params.put("start_date", startDate);
        }
        if (endDate != null) {
            params.put("end_date", endDate);
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept-Language", acceptLanguage);
        return this.get(method, params, headers, new TypeReference<>() {
        });
    }

    /**
     * Get all subscriptions by wallet address.
     *
     * @param accountId Account ID
     * @return CompletableFuture of Subscriptions object containing the list of subscriptions
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<Subscriptions> getSubscriptions(String accountId) throws TONAPIError {
        String method = String.format("v2/accounts/%s/subscriptions", accountId);
        return this.get(method, null, null, new TypeReference<>() {
        });
    }

    /**
     * Update the internal cache for a specific account.
     *
     * @param accountId Account ID
     * @return CompletableFuture<Boolean> true if the reindexing was successful
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<Void> reindex(String accountId) throws TONAPIError {
        String method = String.format("v2/accounts/%s/reindex", accountId);
        return this.post(method, null, null, null, new TypeReference<>() {
        });
    }

    /**
     * Search for accounts by domain name.
     *
     * @param name Domain name to search for
     * @return CompletableFuture of FoundAccounts object containing the list of found accounts
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<FoundAccounts> searchByDomain(String name) throws TONAPIError {
        String method = "v2/accounts/search";
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        return this.get(method, params, null, new TypeReference<>() {
        });
    }

    /**
     * Get expiring .ton DNS domains for an account.
     *
     * @param accountId Account ID
     * @param period    Number of days before expiration (optional)
     * @return CompletableFuture of DnsExpiring object containing expiring domains
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<DnsExpiring> getExpiringDns(String accountId, Integer period) throws TONAPIError {
        String method = String.format("v2/accounts/%s/dns/expiring", accountId);
        Map<String, Object> params = new HashMap<>();
        if (period != null) {
            params.put("period", period);
        }
        return this.get(method, params, null, new TypeReference<>() {
        });
    }

    /**
     * Get the public key by account ID.
     *
     * @param accountId Account ID
     * @return CompletableFuture of PublicKey object containing the public key
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<PublicKey> getPublicKey(String accountId) throws TONAPIError {
        String method = String.format("v2/accounts/%s/publickey", accountId);
        return this.get(method, null, null, new TypeReference<>() {
        });
    }

    /**
     * Get the multisig information for an account.
     *
     * @param accountId Account ID
     * @return CompletableFuture of Multisigs object containing multisig details
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<Multisigs> getAccountMultisigs(String accountId) throws TONAPIError {
        String method = String.format("v2/accounts/%s/multisigs", accountId);
        return this.get(method, null, null, new TypeReference<>() {
        });
    }

    /**
     * Get the balance change of an account.
     *
     * @param accountId Account ID
     * @param startDate Start date (timestamp)
     * @param endDate   End date (timestamp)
     * @return CompletableFuture of BalanceChange object containing the balance change information
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<BalanceChange> getBalanceChange(
            String accountId,
            Long startDate,
            Long endDate) throws TONAPIError {
        String method = String.format("v2/accounts/%s/diff", accountId);
        Map<String, Object> params = new HashMap<>();
        params.put("start_date", startDate);
        params.put("end_date", endDate);
        return this.get(method, params, null, new TypeReference<>() {
        });
    }

    /**
     * Emulate sending a message to the blockchain.
     *
     * @param accountId            Account ID
     * @param body                 Request body containing 'boc'
     * @param acceptLanguage       Accept-Language header value. Default is "en". Example -> ru-RU,ru;q=0.5
     * @param ignoreSignatureCheck Ignore signature check if true
     * @return CompletableFuture of AccountEvent object containing the emulated event
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<AccountEvent> emulateEvent(
            String accountId,
            Map<String, Object> body,
            String acceptLanguage,
            Boolean ignoreSignatureCheck) throws TONAPIError {
        String method = String.format("v2/accounts/%s/events/emulate", accountId);
        Map<String, Object> params = new HashMap<>();
        if (ignoreSignatureCheck != null) {
            params.put("ignore_signature_check", ignoreSignatureCheck);
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept-Language", acceptLanguage);
        return this.post(method, params, body, headers, new TypeReference<>() {
        });
    }
}
