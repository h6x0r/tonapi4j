package org.ton.async.methods;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.ton.utils.Constants.DOMAIN_NAME;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;
import org.ton.async.AsyncTonapiTestBase;
import org.ton.exception.TONAPIError;
import org.ton.schema.dns.Auctions;
import org.ton.schema.dns.DNSRecord;
import org.ton.schema.domain.DomainBids;
import org.ton.schema.domain.DomainInfo;

public class TestDnsMethod extends AsyncTonapiTestBase {

  @Test
  public void testGetInfo() throws TONAPIError, ExecutionException, InterruptedException {
    CompletableFuture<DomainInfo> future = tonapi.getDns().getInfo(DOMAIN_NAME);
    DomainInfo response = future.get();
    assertNotNull(response);
  }

  @Test
  public void testResolve() throws TONAPIError, ExecutionException, InterruptedException {
    CompletableFuture<DNSRecord> future = tonapi.getDns().resolve(DOMAIN_NAME);
    DNSRecord response = future.get();
    assertNotNull(response);
  }

  @Test
  public void testBids() throws TONAPIError, ExecutionException, InterruptedException {
    CompletableFuture<DomainBids> future = tonapi.getDns().bids(DOMAIN_NAME);
    DomainBids response = future.get();
    assertNotNull(response);
  }

  @Test
  public void testGetAuctions() throws TONAPIError, ExecutionException, InterruptedException {
    CompletableFuture<Auctions> auctions = tonapi.getDns().getAuctions(DOMAIN_NAME);
    Auctions response = auctions.get();
    assertNotNull(response);
  }
}
