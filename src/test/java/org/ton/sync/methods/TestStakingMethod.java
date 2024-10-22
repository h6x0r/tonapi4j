package org.ton.sync.methods;

import org.junit.jupiter.api.Test;
import org.ton.schema.stacking.AccountStaking;
import org.ton.schema.stacking.StakingPoolHistory;
import org.ton.schema.stacking.StakingPoolInfo;
import org.ton.schema.stacking.StakingPools;
import org.ton.sync.TonapiTestBase;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.ton.utils.Constants.ACCEPT_LANGUAGE_EN;

public class TestStakingMethod extends TonapiTestBase {

    private static final String ACCOUNT_ID = "EQCMVd9ya3yvhyhOWckBUmrHsle3eLqb_em8npZEmbbR-NOe";
    private static final String POOL_ID = "EQCkWxfyhAkim3g2DjKQQg8T5P4g-Q1-K_jErGcDJZ4i-vqR";

    @Test
    public void testGetPoolInfo() {
        StakingPoolInfo response = tonapi.getStaking().getPoolInfo(POOL_ID, ACCEPT_LANGUAGE_EN);
        assertNotNull(response);
    }

    @Test
    public void testGetPoolHistory() {
        StakingPoolHistory response = tonapi.getStaking().getPoolHistory(POOL_ID);
        assertNotNull(response);
    }

    @Test
    public void testGetParticipatingPools() {
        AccountStaking response = tonapi.getStaking().getParticipatingPools(ACCOUNT_ID);
        assertNotNull(response);
    }

    @Test
    public void testGetAllNetworkPools() {
        StakingPools response = tonapi.getStaking().getAllNetworkPools(ACCOUNT_ID, false, ACCEPT_LANGUAGE_EN);
        assertNotNull(response);
    }
}
