package org.ton.async.methods;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.ton.utils.Constants.ACCOUNT_ID;
import static org.ton.utils.Constants.ACCOUNT_ID_NFT;
import static org.ton.utils.Constants.AFTER_LT;
import static org.ton.utils.Constants.BEFORE_LT;
import static org.ton.utils.Constants.BLOCK_ID;
import static org.ton.utils.Constants.LIMIT;
import static org.ton.utils.Constants.MASTERCHAIN_SEQNO;
import static org.ton.utils.Constants.MESSAGE_ID;
import static org.ton.utils.Constants.TRANSACTION_ID;

import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.ton.async.AsyncTonapiTestBase;
import org.ton.schema.blockchain.BlockchainAccountInspect;
import org.ton.schema.blockchain.BlockchainBlock;
import org.ton.schema.blockchain.BlockchainBlockShards;
import org.ton.schema.blockchain.BlockchainBlocks;
import org.ton.schema.blockchain.BlockchainConfig;
import org.ton.schema.blockchain.BlockchainRawAccount;
import org.ton.schema.blockchain.MethodExecutionResult;
import org.ton.schema.blockchain.RawBlockchainConfig;
import org.ton.schema.blockchain.Validators;
import org.ton.schema.transactions.Transaction;
import org.ton.schema.transactions.Transactions;

public class TestBlockchainMethod extends AsyncTonapiTestBase {

  @Test
  public void testGetBlockData() throws Exception {
    CompletableFuture<BlockchainBlock> future = tonapi.getBlockchain().getBlockData(BLOCK_ID);
    BlockchainBlock response = future.get();
    assertNotNull(response);
  }

  @Test
  public void testGetBlock() throws Exception {
    CompletableFuture<BlockchainBlockShards> future = tonapi.getBlockchain()
        .getBlock(MASTERCHAIN_SEQNO);
    BlockchainBlockShards response = future.get();
    assertNotNull(response);
  }

  @Test
  public void testGetBlocks() throws Exception {
    CompletableFuture<BlockchainBlocks> future = tonapi.getBlockchain()
        .getBlocks(MASTERCHAIN_SEQNO);
    BlockchainBlocks response = future.get();
    assertNotNull(response);
  }

  @Test
  public void testGetTransactionsShards() throws Exception {
    CompletableFuture<Transactions> future = tonapi.getBlockchain()
        .getTransactionsShards(MASTERCHAIN_SEQNO);
    Transactions response = future.get();
    assertNotNull(response);
  }

  @Test
  public void testGetBlockchainConfig() throws Exception {
    CompletableFuture<BlockchainConfig> future = tonapi.getBlockchain()
        .getBlockchainConfig(MASTERCHAIN_SEQNO);
    BlockchainConfig response = future.get();
    assertNotNull(response);
  }

  @Test
  public void testGetRawBlockchainConfig() throws Exception {
    CompletableFuture<RawBlockchainConfig> future = tonapi.getBlockchain()
        .getRawBlockchainConfig(MASTERCHAIN_SEQNO);
    RawBlockchainConfig response = future.get();
    assertNotNull(response);
  }

  @Test
  public void testGetTransactionFromBlock() throws Exception {
    CompletableFuture<Transactions> future = tonapi.getBlockchain()
        .getTransactionFromBlock(BLOCK_ID);
    Transactions response = future.get();
    assertNotNull(response);
  }

  @Test
  public void testGetTransactionData() throws Exception {
    CompletableFuture<Transaction> future = tonapi.getBlockchain()
        .getTransactionData(TRANSACTION_ID);
    Transaction response = future.get();
    assertNotNull(response);
  }

  @Test
  @Disabled
  public void testGetTransactionByMessage() throws Exception {
    CompletableFuture<Transaction> future = tonapi.getBlockchain()
        .getTransactionByMessage(MESSAGE_ID);
    Transaction response = future.get();
    assertNotNull(response);
  }

  @Test
  public void testGetValidators() throws Exception {
    CompletableFuture<Validators> future = tonapi.getBlockchain().getValidators();
    Validators response = future.get();
    assertNotNull(response);
  }

  @Test
  public void testGetLastMasterchainBlock() throws Exception {
    CompletableFuture<BlockchainBlock> future = tonapi.getBlockchain().getLastMasterchainBlock();
    BlockchainBlock response = future.get();
    assertNotNull(response);
  }

  @Test
  public void testGetAccountInfo() throws Exception {
    CompletableFuture<BlockchainRawAccount> future = tonapi.getBlockchain()
        .getAccountInfo(ACCOUNT_ID);
    BlockchainRawAccount response = future.get();
    assertNotNull(response);
  }

  @Test
  public void testGetAccountTransactions() throws Exception {
    CompletableFuture<Transactions> future = tonapi.getBlockchain()
        .getAccountTransactions(ACCOUNT_ID, AFTER_LT, BEFORE_LT, LIMIT);
    Transactions response = future.get();
    assertNotNull(response);
  }

  @Test
  public void testInspectAccount() throws Exception {
    CompletableFuture<BlockchainAccountInspect> future = tonapi.getBlockchain()
        .inspectAccount(ACCOUNT_ID);
    BlockchainAccountInspect response = future.get();
    assertNotNull(response);
  }

  @Test
  public void testGetConfig() throws Exception {
    CompletableFuture<BlockchainConfig> future = tonapi.getBlockchain().getConfig();
    BlockchainConfig response = future.get();
    assertNotNull(response);
  }

  @Test
  public void testGetRawConfig() throws Exception {
    CompletableFuture<RawBlockchainConfig> future = tonapi.getBlockchain().getRawConfig();
    RawBlockchainConfig response = future.get();
    assertNotNull(response);
  }

  @Test
  public void testExecuteGetMethod() throws Exception {
    CompletableFuture<MethodExecutionResult> future = tonapi.getBlockchain()
        .executeGetMethod(ACCOUNT_ID_NFT, "get_nft_data");
    MethodExecutionResult response = future.get();
    assertNotNull(response);
  }
}
