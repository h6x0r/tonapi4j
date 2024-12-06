package org.ton.tonapi.async.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.ton.exception.TONAPIError;
import org.ton.schema.stacking.AccountStaking;
import org.ton.schema.stacking.StakingPoolHistory;
import org.ton.schema.stacking.StakingPoolInfo;
import org.ton.schema.stacking.StakingPools;
import org.ton.tonapi.async.AsyncTonapiClientBase;

public class StakingMethod extends AsyncTonapiClientBase {

  public StakingMethod(AsyncTonapiClientBase client) {
    super(client);
  }

  /**
   * All pools where account participates.
   *
   * @param accountId Account ID
   * @return CompletableFuture of AccountStaking object containing the staking information
   * @throws TONAPIError if the request fails
   */
  public CompletableFuture<AccountStaking> getParticipatingPools(String accountId)
      throws TONAPIError {
    String method = String.format("v2/staking/nominator/%s/pools", accountId);
    return this.get(method, null, null, new TypeReference<AccountStaking>() {
    });
  }

  /**
   * Staking pool info.
   *
   * @param accountId      Account ID
   * @param acceptLanguage Accept-Language header value. Default is "en". Example -> ru-RU,ru;q=0.5
   * @return CompletableFuture of StakingPoolInfo object containing the pool info
   * @throws TONAPIError if the request fails
   */
  public CompletableFuture<StakingPoolInfo> getPoolInfo(String accountId, String acceptLanguage)
      throws TONAPIError {
    String method = String.format("v2/staking/pool/%s", accountId);
    Map<String, String> headers = new HashMap<>();
    headers.put("Accept-Language", acceptLanguage != null ? acceptLanguage : "en");
    return this.get(method, null, headers, new TypeReference<StakingPoolInfo>() {
    });
  }

  /**
   * Staking pool history.
   *
   * @param accountId Account ID
   * @return CompletableFuture of StakingPoolHistory object containing the pool history
   * @throws TONAPIError if the request fails
   */
  public CompletableFuture<StakingPoolHistory> getPoolHistory(String accountId) throws TONAPIError {
    String method = String.format("v2/staking/pool/%s/history", accountId);
    return this.get(method, null, null, new TypeReference<StakingPoolHistory>() {
    });
  }

  /**
   * All pools available in network.
   *
   * @param availableFor      Account ID
   * @param includeUnverified Return also pools not from white list - just compatible by interfaces
   * @param acceptLanguage    Accept-Language header value. Default is "en". Example ->
   *                          ru-RU,ru;q=0.5
   * @return CompletableFuture of StakingPools object containing all network pools
   * @throws TONAPIError if the request fails
   */
  public CompletableFuture<StakingPools> getAllNetworkPools(String availableFor,
      boolean includeUnverified, String acceptLanguage) throws TONAPIError {
    String method = "v2/staking/pools";
    Map<String, Object> params = new HashMap<>();
    params.put("available_for", availableFor);
    params.put("include_unverified", includeUnverified);
    Map<String, String> headers = new HashMap<>();
    headers.put("Accept-Language", acceptLanguage != null ? acceptLanguage : "en");
    return this.get(method, params, headers, new TypeReference<StakingPools>() {
    });
  }
}
