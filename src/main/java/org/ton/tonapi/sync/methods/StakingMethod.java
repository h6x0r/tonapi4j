package org.ton.tonapi.sync.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.HashMap;
import java.util.Map;
import org.ton.exception.TONAPIError;
import org.ton.schema.stacking.AccountStaking;
import org.ton.schema.stacking.StakingPoolHistory;
import org.ton.schema.stacking.StakingPoolInfo;
import org.ton.schema.stacking.StakingPools;
import org.ton.tonapi.sync.TonapiClientBase;

public class StakingMethod extends TonapiClientBase {

  public StakingMethod(TonapiClientBase client) {
    super(client);
  }

  /**
   * All pools where account participates.
   *
   * @param accountId Account ID
   * @return AccountStaking object containing the staking information
   * @throws TONAPIError if the request fails
   */
  public AccountStaking getParticipatingPools(String accountId) throws TONAPIError {
    String method = String.format("v2/staking/nominator/%s/pools", accountId);
    return this.get(method, null, null, new TypeReference<AccountStaking>() {
    });
  }

  /**
   * Staking pool info.
   *
   * @param accountId      Account ID
   * @param acceptLanguage Accept-Language header value. Default is "en". Example -> ru-RU,ru;q=0.5
   * @return StakingPoolInfo object containing the pool info
   * @throws TONAPIError if the request fails
   */
  public StakingPoolInfo getPoolInfo(String accountId, String acceptLanguage) throws TONAPIError {
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
   * @return StakingPoolHistory object containing the pool history
   * @throws TONAPIError if the request fails
   */
  public StakingPoolHistory getPoolHistory(String accountId) throws TONAPIError {
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
   * @return StakingPools object containing all network pools
   * @throws TONAPIError if the request fails
   */
  public StakingPools getAllNetworkPools(String availableFor, boolean includeUnverified,
      String acceptLanguage) throws TONAPIError {
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
