package org.ton.sync.methods;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.ton.utils.Constants.DOMAIN_NAME;

import org.junit.jupiter.api.Test;
import org.ton.exception.TONAPIError;
import org.ton.schema.dns.Auctions;
import org.ton.schema.dns.DNSRecord;
import org.ton.schema.domain.DomainBids;
import org.ton.schema.domain.DomainInfo;
import org.ton.sync.TonapiTestBase;

public class TestDnsMethod extends TonapiTestBase {

  @Test
  public void testGetInfo() throws TONAPIError {
    DomainInfo response = tonapi.getDns().getInfo(DOMAIN_NAME);
    assertNotNull(response);
  }

  @Test
  public void testResolve() throws TONAPIError {
    DNSRecord response = tonapi.getDns().resolve(DOMAIN_NAME);
    assertNotNull(response);
  }

  @Test
  public void testBids() throws TONAPIError {
    DomainBids response = tonapi.getDns().bids(DOMAIN_NAME);
    assertNotNull(response);
  }

  @Test
  public void testGetAuctions() throws TONAPIError {
    Auctions response = tonapi.getDns().getAuctions(DOMAIN_NAME);
    assertNotNull(response);
  }
}
