package org.ton.tonapi.async.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import org.ton.exception.TONAPIError;
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
import org.ton.tonapi.async.AsyncTonapiClientBase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class LiteserverMethod extends AsyncTonapiClientBase {

    public LiteserverMethod(AsyncTonapiClientBase client) {
        super(client);
    }

    /**
     * Get raw masterchain info.
     *
     * @return CompletableFuture of RawMasterChainInfo object containing the masterchain info
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<RawMasterChainInfo> getMasterchainInfo() throws TONAPIError {
        String method = "v2/liteserver/get_masterchain_info";
        return this.get(method, null, null, new TypeReference<RawMasterChainInfo>() {
        });
    }

    /**
     * Get raw masterchain extended info.
     *
     * @param mode Mode parameter
     * @return CompletableFuture of RawMasterChainInfoExt object containing the extended masterchain info
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<RawMasterChainInfoExt> getMasterchainInfoExt(int mode) throws TONAPIError {
        String method = "v2/liteserver/get_masterchain_info_ext";
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        return this.get(method, params, null, new TypeReference<RawMasterChainInfoExt>() {
        });
    }

    /**
     * Get raw time.
     *
     * @return CompletableFuture<Long> Time as Long, or null if not available
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<Long> getTime() throws TONAPIError {
        String method = "v2/liteserver/get_time";
        return this.get(method, null, null, new TypeReference<Map<String, Object>>() {
                })
                .thenApply(response -> {
                    Object time = response.get("time");
                    return time != null ? ((Number) time).longValue() : null;
                });
    }

    /**
     * Get raw blockchain block.
     *
     * @param blockId Block ID: (workchain, shard, seqno, root_hash, file_hash)
     * @return CompletableFuture of RawGetBlock object containing the block data
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<RawGetBlock> getRawBlock(String blockId) throws TONAPIError {
        String method = String.format("v2/liteserver/get_block/%s", blockId);
        return this.get(method, null, null, new TypeReference<RawGetBlock>() {
        });
    }

    /**
     * Get raw blockchain block state.
     *
     * @param blockId Block ID: (workchain, shard, seqno, root_hash, file_hash)
     * @return CompletableFuture of RawBlockState object containing the block state
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<RawBlockState> getRawState(String blockId) throws TONAPIError {
        String method = String.format("v2/liteserver/get_state/%s", blockId);
        return this.get(method, null, null, new TypeReference<RawBlockState>() {
        });
    }

    /**
     * Get raw blockchain block header.
     *
     * @param blockId Block ID: (workchain, shard, seqno, root_hash, file_hash)
     * @param mode    Mode parameter
     * @return CompletableFuture of RawBlockHeader object containing the block header
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<RawBlockHeader> getRawHeader(String blockId, int mode) throws TONAPIError {
        String method = String.format("v2/liteserver/get_block_header/%s", blockId);

        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);

        return this.get(method, params, null, new TypeReference<RawBlockHeader>() {
        });
    }

    /**
     * Send raw message to blockchain.
     *
     * @param bodyMsg the base64 serialized bag-of-cells Example -> te6ccgECBQEAARUAAkWIAWTtae+KgtbrX26Bep8JSq8lFLfGOoyGR/xwdjfvpvEaHg
     * @return CompletableFuture<Integer> status code 200
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<Integer> sendMessage(String bodyMsg) throws TONAPIError {
        String method = "v2/liteserver/send_message";

        Map<String, Object> body = new HashMap<>();
        body.put("body", bodyMsg);

        return this.post(method, null, body, null, new TypeReference<Map<String, Object>>() {
                })
                .thenApply(response -> {
                    Object code = response.get("code");
                    return code != null ? ((Number) code).intValue() : null;
                });
    }

    /**
     * Get raw account state.
     *
     * @param accountAddress Account ID
     * @param targetBlock    Optional target block: (workchain, shard, seqno, root_hash, file_hash)
     * @return CompletableFuture of RawAccountState object containing the account state
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<RawAccountState> getAccountState(String accountAddress, String targetBlock) throws TONAPIError {
        String method = String.format("v2/liteserver/get_account_state/%s", accountAddress);
        Map<String, Object> params = new HashMap<>();
        if (targetBlock != null && !targetBlock.isEmpty()) {
            params.put("target_block", targetBlock);
        }
        return this.get(method, params.isEmpty() ? null : params, null, new TypeReference<RawAccountState>() {
        });
    }

    /**
     * Get raw shard info.
     *
     * @param blockId   Block ID: (workchain, shard, seqno, root_hash, file_hash)
     * @param workchain Workchain
     * @param shard     Shard
     * @param exact     Exact flag
     * @return CompletableFuture of RawShardInfo object containing the shard info
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<RawShardInfo> getShardInfo(String blockId, Long workchain, Long shard, boolean exact) throws TONAPIError {
        String method = String.format("v2/liteserver/get_shard_info/%s", blockId);
        Map<String, Object> params = new HashMap<>();
        params.put("workchain", workchain);
        params.put("shard", shard);
        params.put("exact", exact);
        return this.get(method, params, null, new TypeReference<RawShardInfo>() {
        });
    }

    /**
     * Get all raw shards' info.
     *
     * @param blockId Block ID: (workchain, shard, seqno, root_hash, file_hash)
     * @return CompletableFuture of RawShardsInfo object containing all shards info
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<RawShardsInfo> getAllRawShardsInfo(String blockId) throws TONAPIError {
        String method = String.format("v2/liteserver/get_all_shards_info/%s", blockId);
        return this.get(method, null, null, new TypeReference<RawShardsInfo>() {
        });
    }

    /**
     * Get raw transactions.
     *
     * @param accountId Account ID
     * @param lt        Logical time (lt)
     * @param hash      Hash
     * @param count     Number of transactions to retrieve
     * @return CompletableFuture of RawTransactions object containing the transactions
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<RawTransactions> getRawTransactions(String accountId, long lt, String hash, int count) throws TONAPIError {
        String method = String.format("v2/liteserver/get_transactions/%s", accountId);
        Map<String, Object> params = new HashMap<>();
        params.put("lt", lt);
        params.put("hash", hash);
        params.put("count", count);
        return this.get(method, params, null, new TypeReference<RawTransactions>() {
        });
    }

    /**
     * Get raw list of block transactions.
     *
     * @param blockId   Block ID: (workchain, shard, seqno, root_hash, file_hash)
     * @param mode      Mode parameter
     * @param count     Number of transactions to retrieve
     * @param accountId Optional account ID
     * @param lt        Optional logical time (lt)
     * @return CompletableFuture of RawListBlockTransactions object containing the list of block transactions
     * @throws TONAPIError if the request fails
     * @implNote block ID: (workchain,shard,seqno,root_hash,file_hash)
     */
    public CompletableFuture<RawListBlockTransactions> getRawListBlockTransaction(
            String blockId,
            int mode,
            int count,
            String accountId,
            Long lt) throws TONAPIError {
        String method = String.format("v2/liteserver/list_block_transactions/%s", blockId);
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        params.put("count", count);
        if (accountId != null && !accountId.isEmpty()) {
            params.put("account_id", accountId);
        }
        if (lt != null) {
            params.put("lt", lt);
        }
        return this.get(method, params, null, new TypeReference<RawListBlockTransactions>() {
        });
    }

    /**
     * Get raw block proof.
     *
     * @param knownBlock  Known block: (workchain, shard, seqno, root_hash, file_hash)
     * @param mode        Mode parameter, default is 0
     * @param targetBlock Optional target block: (workchain, shard, seqno, root_hash, file_hash)
     * @return CompletableFuture of RawBlockProof object containing the block proof
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<RawBlockProof> getBlockProof(String knownBlock, int mode, String targetBlock) throws TONAPIError {
        String method = String.format("v2/liteserver/get_block_proof/%s", knownBlock);
        Map<String, Object> params = new HashMap<>();
        params.put("known_block", knownBlock);
        params.put("mode", mode);
        if (targetBlock != null && !targetBlock.isEmpty()) {
            params.put("target_block", targetBlock);
        }
        return this.get(method, params, null, new TypeReference<RawBlockProof>() {
        });
    }

    /**
     * Get raw config.
     *
     * @param blockId Block ID: (workchain, shard, seqno, root_hash, file_hash)
     * @param mode    Mode parameter
     * @return CompletableFuture of RawConfig object containing the configuration
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<RawConfig> getConfigAll(String blockId, int mode) throws TONAPIError {
        String method = String.format("v2/liteserver/get_config_all/%s", blockId);
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        return this.get(method, params, null, new TypeReference<RawConfig>() {
        });
    }

    /**
     * Get raw shard block proof.
     *
     * @param blockId Block ID: (workchain, shard, seqno, root_hash, file_hash)
     * @return CompletableFuture of RawShardProof object containing the shard block proof
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<RawShardProof> getShardBlockProof(String blockId) throws TONAPIError {
        String method = String.format("v2/liteserver/get_shard_block_proof/%s", blockId);
        return this.get(method, null, null, new TypeReference<RawShardProof>() {
        });
    }

    /**
     * Get out message queue sizes.
     *
     * @return CompletableFuture of OutMsgQueueSize object containing the queue sizes
     * @throws TONAPIError if the request fails
     */
    public CompletableFuture<OutMsgQueueSize> getOutMsgQueueSize() throws TONAPIError {
        String method = "v2/liteserver/get_out_msg_queue_size";
        return this.get(method, null, null, new TypeReference<OutMsgQueueSize>() {
        });
    }
}
