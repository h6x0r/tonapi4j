package org.ton.sync.methods;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.ton.utils.Constants.ACCOUNT_ID;

import org.junit.jupiter.api.Test;
import org.ton.schema.utils.AddressForm;
import org.ton.schema.utils.ServiceStatus;
import org.ton.sync.TonapiTestBase;

public class TestUtilitiesMethod extends TonapiTestBase {

  @Test
  public void testParseAddress() {
    AddressForm response = tonapi.getUtilities().parseAddress(ACCOUNT_ID);
    assertNotNull(response);
  }

  @Test
  public void testStatus() {
    ServiceStatus response = tonapi.getUtilities().status();
    assertNotNull(response);
  }
}
