package org.ton.tonapi.sync.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.HashMap;
import java.util.Map;
import org.ton.exception.TONAPIError;
import org.ton.schema.dns.Auctions;
import org.ton.schema.dns.DNSRecord;
import org.ton.schema.domain.DomainBids;
import org.ton.schema.domain.DomainInfo;
import org.ton.tonapi.sync.TonapiClientBase;

public class DnsMethod extends TonapiClientBase {

  public DnsMethod(TonapiClientBase client) {
    super(client);
  }

  /**
   * Get full information about a domain name.
   *
   * @param domainName Domain name with .ton or .t.me
   * @return DomainInfo object containing the domain information
   * @throws TONAPIError if the request fails
   */
  public DomainInfo getInfo(String domainName) throws TONAPIError {
    String method = String.format("v2/dns/%s", domainName);
    return this.get(method, null, null, new TypeReference<DomainInfo>() {
    });
  }

  /**
   * DNS resolve for a domain name.
   *
   * @param domainName Domain name with .ton or .t.me
   * @return DNSRecord object containing the DNS records
   * @throws TONAPIError if the request fails
   */
  public DNSRecord resolve(String domainName) throws TONAPIError {
    String method = String.format("v2/dns/%s/resolve", domainName);
    return this.get(method, null, null, new TypeReference<DNSRecord>() {
    });
  }

  /**
   * Get domain bids.
   *
   * @param domainName Domain name with .ton or .t.me
   * @return DomainBids object containing the bids for the domain
   * @throws TONAPIError if the request fails
   */
  public DomainBids bids(String domainName) throws TONAPIError {
    String method = String.format("v2/dns/%s/bids", domainName);
    return this.get(method, null, null, new TypeReference<DomainBids>() {
    });
  }

  /**
   * Get all auctions.
   *
   * @param tld Domain filter for current auctions ("ton" or "t.me"), default is "ton"
   * @return Auctions object containing the list of auctions
   * @throws TONAPIError if the request fails
   */
  public Auctions getAuctions(String tld) throws TONAPIError {
    String method = "v2/dns/auctions";
    Map<String, Object> params = new HashMap<>();
    params.put("tld", tld);
    return this.get(method, params, null, new TypeReference<Auctions>() {
    });
  }
}
