package org.ton.async.methods;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.ton.utils.Constants.ACCOUNT_ID;

import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;
import org.ton.async.AsyncTonapiTestBase;
import org.ton.schema.utils.AddressForm;
import org.ton.schema.utils.ServiceStatus;

public class TestUtilitiesMethod extends AsyncTonapiTestBase {


  @Test
  public void testParseAddress() throws Exception {
    CompletableFuture<AddressForm> future = tonapi.getUtilities().parseAddress(ACCOUNT_ID);
    AddressForm response = future.get();
    assertNotNull(response);
  }

  @Test
  public void testStatus() throws Exception {
    CompletableFuture<ServiceStatus> future = tonapi.getUtilities().status();
    ServiceStatus response = future.get();
    assertNotNull(response);
  }
}
