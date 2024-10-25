package org.ton.async.methods;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;
import org.ton.async.AsyncTonapiTestBase;
import org.ton.exception.TONAPIError;
import org.ton.schema.gasless.GaslessConfig;
import org.ton.schema.gasless.SignRawParams;
import org.ton.tonapi.async.methods.GaslessMethod;

public class TestGaslessMethod extends AsyncTonapiTestBase {

  // USDT Jetton Master Address
  private static final String JETTON_MASTER_ADDRESS = "EQCxE6mUtQJKFnGfaROTKOt1lZbDiiX1kCixRv7Nw2Id_sDs";
  private static final String ACCOUNT_ID = "WALLET_ADDRESS";
  private static final String WALLET_PUBLIC_KEY = "WALLET_PUBLIC_KEY";
  private static final List<String> MESSAGE_LIST = Arrays.asList(
      "te6ccgECBQEAARUAAkWIAWTtae+KgtbrX26Bep8JSq8lFLfGOoyGR/xwdjfvpvEaHg");
  private static final String BOC = "te6ccgECBQEAARUAAkWIAWTtae+KgtbrX26Bep8JSq8lFLfGOoyGR/xwdjfvpvEaHg";
  private final GaslessMethod gaslessMethod = tonapi.getGasless();

  @Test
  public void testGetConfig() throws TONAPIError, ExecutionException, InterruptedException {
    CompletableFuture<GaslessConfig> future = gaslessMethod.getConfig();
    GaslessConfig config = future.get();

    assertNotNull(config, "GaslessConfig should not be null");
    assertNotNull(config.getRelayAddress(), "RelayAddress should not be null");
    assertNotNull(config.getGasJettons(), "GasJettons should not be null");
  }

  @Test
  public void testEstimateGasPrice() throws TONAPIError, ExecutionException, InterruptedException {
    CompletableFuture<SignRawParams> future = gaslessMethod.estimateGasPrice(
        JETTON_MASTER_ADDRESS,
        ACCOUNT_ID,
        WALLET_PUBLIC_KEY,
        MESSAGE_LIST
    );
    SignRawParams params = future.get();

    assertNotNull(params, "SignRawParams should not be null");
    assertNotNull(params.getRelayAddress(), "RelayAddress should not be null");
    assertNotNull(params.getCommission(), "Commission should not be null");
    assertNotNull(params.getFrom(), "From should not be null");
    assertNotNull(params.getValidUntil(), "ValidUntil should not be null");
    assertNotNull(params.getMessages(), "Messages should not be null");
  }

  @Test
  public void testSend() throws TONAPIError, ExecutionException, InterruptedException {
    CompletableFuture<Boolean> future = gaslessMethod.send(WALLET_PUBLIC_KEY, BOC);

    assertTrue(future.get(), "Send method should return true");
  }
}
