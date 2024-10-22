package org.ton.tonapi.async.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import org.ton.exception.TONAPIError;
import org.ton.schema.dns.Auctions;
import org.ton.schema.dns.DNSRecord;
import org.ton.schema.domain.DomainBids;
import org.ton.schema.domain.DomainInfo;
import org.ton.tonapi.async.AsyncTonapiClientBase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class DnsMethod extends AsyncTonapiClientBase {

    public DnsMethod(AsyncTonapiClientBase client) {
        super(client);
    }

    /**
     * Get full information about a domain name.
     *
     * @param domainName Domain name with .ton or .t.me
     * @return CompletableFuture of DomainInfo object containing the domain information
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<DomainInfo> getInfo(String domainName) throws TONAPIError {
        String method = String.format("v2/dns/%s", domainName);
        return this.get(method, null, null, new TypeReference<>() {
        });
    }

    /**
     * DNS resolve for a domain name.
     *
     * @param domainName Domain name with .ton or .t.me
     * @return CompletableFuture of DNSRecord object containing the DNS records
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<DNSRecord> resolve(String domainName) throws TONAPIError {
        String method = String.format("v2/dns/%s/resolve", domainName);
        return this.get(method, null, null, new TypeReference<>() {
        });
    }

    /**
     * Get domain bids.
     *
     * @param domainName Domain name with .ton or .t.me
     * @return CompletableFuture of DomainBids object containing the bids for the domain
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<DomainBids> bids(String domainName) throws TONAPIError {
        String method = String.format("v2/dns/%s/bids", domainName);
        return this.get(method, null, null, new TypeReference<>() {
        });
    }

    /**
     * Get all auctions.
     *
     * @param tld Domain filter for current auctions ("ton" or "t.me"), default is "ton"
     * @return CompletableFuture of Auctions object containing the list of auctions
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<Auctions> getAuctions(String tld) throws TONAPIError {
        String method = "v2/dns/auctions";
        Map<String, Object> params = new HashMap<>();
        params.put("tld", tld);
        return this.get(method, params, null, new TypeReference<>() {
        });
    }
}
