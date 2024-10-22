package org.ton.async.methods;

import org.junit.jupiter.api.Test;
import org.ton.async.AsyncTonapiTestBase;
import org.ton.schema.stacking.AccountStaking;
import org.ton.schema.stacking.StakingPoolHistory;
import org.ton.schema.stacking.StakingPoolInfo;
import org.ton.schema.stacking.StakingPools;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.ton.utils.Constants.ACCEPT_LANGUAGE_EN;

public class TestStakingMethod extends AsyncTonapiTestBase {

    private static final String ACCOUNT_ID = "EQCMVd9ya3yvhyhOWckBUmrHsle3eLqb_em8npZEmbbR-NOe";
    private static final String POOL_ID = "EQCkWxfyhAkim3g2DjKQQg8T5P4g-Q1-K_jErGcDJZ4i-vqR";

    @Test
    public void testGetPoolInfo() throws Exception {
        CompletableFuture<StakingPoolInfo> future = tonapi.getStaking().getPoolInfo(POOL_ID, ACCEPT_LANGUAGE_EN);
        StakingPoolInfo response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetPoolHistory() throws Exception {
        CompletableFuture<StakingPoolHistory> future = tonapi.getStaking().getPoolHistory(POOL_ID);
        StakingPoolHistory response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetParticipatingPools() throws Exception {
        CompletableFuture<AccountStaking> future = tonapi.getStaking().getParticipatingPools(ACCOUNT_ID);
        AccountStaking response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetAllNetworkPools() throws Exception {
        boolean includeUnverified = false;
        String acceptLanguage = "en";
        CompletableFuture<StakingPools> future = tonapi.getStaking().getAllNetworkPools(ACCOUNT_ID, includeUnverified, acceptLanguage);
        StakingPools response = future.get();
        assertNotNull(response);
    }
}
