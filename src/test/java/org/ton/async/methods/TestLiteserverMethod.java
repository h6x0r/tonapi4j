package org.ton.async.methods;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.ton.async.AsyncTonapiTestBase;
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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.ton.utils.Constants.ACCOUNT_ID;
import static org.ton.utils.Constants.BEFORE_LT;
import static org.ton.utils.Constants.COUNT;
import static org.ton.utils.Constants.MESSAGE_ID;
import static org.ton.utils.Constants.MODE;
import static org.ton.utils.Constants.SHARD;
import static org.ton.utils.Constants.TRANSACTION_ID;
import static org.ton.utils.Constants.WC;

public class TestLiteserverMethod extends AsyncTonapiTestBase {

    private static final String BLOCK_ID = "(0,6000000000000000,46499338,d672da3714e6535e7ffea539528da9f1fe9ba093dc1afefda30dd234f1005ca3,4f7f1ee49e236a5866f680a4f7d549dd19218e151d213a0e24537e3b96a2170d)";

    @Test
    public void testGetAccountState() throws Exception {
        CompletableFuture<RawAccountState> future = tonapi.getLiteserver().getAccountState(ACCOUNT_ID, null);
        RawAccountState response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetMasterchainInfo() throws Exception {
        CompletableFuture<RawMasterChainInfo> future = tonapi.getLiteserver().getMasterchainInfo();
        RawMasterChainInfo response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetMasterchainInfoExt() throws Exception {
        CompletableFuture<RawMasterChainInfoExt> future = tonapi.getLiteserver().getMasterchainInfoExt(MODE);
        RawMasterChainInfoExt response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetTime() throws Exception {
        CompletableFuture<Long> future = tonapi.getLiteserver().getTime();
        Long response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetRawBlock() throws Exception {
        CompletableFuture<RawGetBlock> future = tonapi.getLiteserver().getRawBlock(BLOCK_ID);
        RawGetBlock response = future.get();
        assertNotNull(response);
    }

    @Test
    @Disabled
    public void testGetRawState() throws Exception {
        CompletableFuture<RawBlockState> future = tonapi.getLiteserver().getRawState(BLOCK_ID);
        RawBlockState response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetRawHeader() throws Exception {
        CompletableFuture<RawBlockHeader> future = tonapi.getLiteserver().getRawHeader(BLOCK_ID, MODE);
        RawBlockHeader response = future.get();
        assertNotNull(response);
    }

    @Test
    @Disabled
    public void testSendMessage() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("body", MESSAGE_ID);

        CompletableFuture<Integer> future = tonapi.getLiteserver().sendMessage(body);
        Integer response = future.get();
        assertNotNull(response);
    }

    @Test
    @Disabled
    public void testGetShardInfo() throws Exception {
        CompletableFuture<RawShardInfo> future = tonapi.getLiteserver().getShardInfo(BLOCK_ID, WC, SHARD, true);
        RawShardInfo response = future.get();
        assertNotNull(response);
    }

    @Test
    @Disabled
    public void testGetAllRawShardsInfo() throws Exception {
        CompletableFuture<RawShardsInfo> future = tonapi.getLiteserver().getAllRawShardsInfo(BLOCK_ID);
        RawShardsInfo response = future.get();
        assertNotNull(response);
    }

    @Test
    @Disabled
    public void testGetRawTransactions() throws Exception {
        CompletableFuture<RawTransactions> future = tonapi.getLiteserver().getRawTransactions(ACCOUNT_ID, BEFORE_LT, TRANSACTION_ID, COUNT);
        RawTransactions response = future.get();
        assertNotNull(response);
    }

    @Test
    @Disabled
    public void testGetRawListBlockTransaction() throws Exception {
        CompletableFuture<RawListBlockTransactions> future = tonapi.getLiteserver().getRawListBlockTransaction(BLOCK_ID, MODE, COUNT, ACCOUNT_ID, BEFORE_LT);
        RawListBlockTransactions response = future.get();
        assertNotNull(response);
    }

    @Test
    @Disabled
    public void testGetBlockProof() throws Exception {
        CompletableFuture<RawBlockProof> future = tonapi.getLiteserver().getBlockProof(BLOCK_ID, MODE, null);
        RawBlockProof response = future.get();
        assertNotNull(response);
    }

    @Test
    @Disabled
    public void testGetConfigAll() throws Exception {
        CompletableFuture<RawConfig> future = tonapi.getLiteserver().getConfigAll(BLOCK_ID, MODE);
        RawConfig response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetShardBlockProof() throws Exception {
        CompletableFuture<RawShardProof> future = tonapi.getLiteserver().getShardBlockProof(BLOCK_ID);
        RawShardProof response = future.get();
        assertNotNull(response);
    }

    @Test
    @Disabled
    public void testGetOutMsgQueueSize() throws Exception {
        CompletableFuture<OutMsgQueueSize> future = tonapi.getLiteserver().getOutMsgQueueSize();
        OutMsgQueueSize response = future.get();
        assertNotNull(response);
    }

}
