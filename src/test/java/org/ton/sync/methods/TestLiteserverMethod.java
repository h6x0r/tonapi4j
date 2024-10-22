package org.ton.sync.methods;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.ton.schema.liteserver.OutMsgQueueSize;
import org.ton.schema.liteserver.RawAccountState;
import org.ton.schema.liteserver.RawBlockHeader;
import org.ton.schema.liteserver.RawBlockProof;
import org.ton.schema.liteserver.RawBlockState;
import org.ton.schema.liteserver.RawConfig;
import org.ton.schema.liteserver.RawGetBlock;
import org.ton.schema.liteserver.RawListBlockTransactions;
import org.ton.schema.liteserver.RawMasterChainInfo;
import org.ton.schema.liteserver.RawMasterChainInfoExt;
import org.ton.schema.liteserver.RawShardInfo;
import org.ton.schema.liteserver.RawShardProof;
import org.ton.schema.liteserver.RawShardsInfo;
import org.ton.schema.liteserver.RawTransactions;
import org.ton.sync.TonapiTestBase;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.ton.utils.Constants.ACCOUNT_ID;
import static org.ton.utils.Constants.BEFORE_LT;
import static org.ton.utils.Constants.COUNT;
import static org.ton.utils.Constants.MESSAGE_ID;
import static org.ton.utils.Constants.MODE;
import static org.ton.utils.Constants.SHARD;
import static org.ton.utils.Constants.TRANSACTION_ID;
import static org.ton.utils.Constants.WC;

public class TestLiteserverMethod extends TonapiTestBase {

    private static final String BLOCK_ID = "(0,c000000000000000,42737975,0b84d6e10979b232e984a51445cfcd2cfbad5005120cd5a7a636118951f4cd04,c5c718105371c9ecc1797284560c42dd1358ec9e84c519719df16baf857d0610)";

    @Test
    public void testGetAccountState() {
        RawAccountState response = tonapi.getLiteserver().getAccountState(ACCOUNT_ID, null);
        assertNotNull(response);
    }

    @Test
    public void testGetMasterchainInfo() {
        RawMasterChainInfo response = tonapi.getLiteserver().getMasterchainInfo();
        assertNotNull(response);
    }

    @Test
    public void testGetMasterchainInfoExt() {
        RawMasterChainInfoExt response = tonapi.getLiteserver().getMasterchainInfoExt(MODE);
        assertNotNull(response);
    }

    @Test
    public void testGetTime() {
        Long response = tonapi.getLiteserver().getTime();
        assertNotNull(response);
    }

    @Test
    @Disabled
    public void testGetRawBlock() {
        RawGetBlock response = tonapi.getLiteserver().getRawBlock(BLOCK_ID);
        assertNotNull(response);
    }

    @Test
    @Disabled
    public void testGetRawState() {
        RawBlockState response = tonapi.getLiteserver().getRawState(BLOCK_ID);
        assertNotNull(response);
    }

    @Test
    @Disabled
    public void testGetRawHeader() {
        RawBlockHeader response = tonapi.getLiteserver().getRawHeader(BLOCK_ID, MODE);
        assertNotNull(response);
    }

    @Test
    @Disabled
    public void testSendMessage() {
        Map<String, Object> body = new HashMap<>();
        body.put("body", MESSAGE_ID);

        Integer response = tonapi.getLiteserver().sendMessage(body);
        assertNotNull(response);
    }

    @Test
    @Disabled
    public void testGetShardInfo() {
        RawShardInfo response = tonapi.getLiteserver().getShardInfo(BLOCK_ID, WC, SHARD, true);
        assertNotNull(response);
    }

    @Test
    @Disabled
    public void testGetAllRawShardsInfo() {
        RawShardsInfo response = tonapi.getLiteserver().getAllRawShardsInfo(BLOCK_ID);
        assertNotNull(response);
    }

    @Test
    @Disabled
    public void testGetRawTransactions() {
        RawTransactions response = tonapi.getLiteserver().getRawTransactions(ACCOUNT_ID, BEFORE_LT, TRANSACTION_ID, COUNT);
        assertNotNull(response);
    }

    @Test
    @Disabled
    public void testGetRawListBlockTransaction() {
        RawListBlockTransactions response = tonapi.getLiteserver().getRawListBlockTransaction(BLOCK_ID, MODE, COUNT, ACCOUNT_ID, BEFORE_LT);
        assertNotNull(response);
    }

    @Test
    @Disabled
    public void testGetBlockProof() {
        RawBlockProof response = tonapi.getLiteserver().getBlockProof(BLOCK_ID, MODE, null);
        assertNotNull(response);
    }

    @Test
    @Disabled
    public void testGetConfigAll() {
        RawConfig response = tonapi.getLiteserver().getConfigAll(BLOCK_ID, MODE);
        assertNotNull(response);
    }

    @Test
    @Disabled
    public void testGetShardBlockProof() {
        RawShardProof response = tonapi.getLiteserver().getShardBlockProof(BLOCK_ID);
        assertNotNull(response);
    }

    @Test
    @Disabled
    public void testGetOutMsgQueueSize() {
        OutMsgQueueSize response = tonapi.getLiteserver().getOutMsgQueueSize();
        assertNotNull(response);
    }

}
