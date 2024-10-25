package org.ton.sync.methods;

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

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
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
import org.ton.sync.TonapiTestBase;

public class TestBlockchainMethod extends TonapiTestBase {

  @Test
  public void testGetBlockData() {
    BlockchainBlock response = tonapi.getBlockchain().getBlockData(BLOCK_ID);
    assertNotNull(response);
  }

  @Test
  public void testGetBlock() {
    BlockchainBlockShards response = tonapi.getBlockchain().getBlock(MASTERCHAIN_SEQNO);
    assertNotNull(response);
  }

  @Test
  public void testGetBlocks() {
    BlockchainBlocks response = tonapi.getBlockchain().getBlocks(MASTERCHAIN_SEQNO);
    assertNotNull(response);
  }

  @Test
  public void testGetTransactionsShards() {
    Transactions response = tonapi.getBlockchain().getTransactionsShards(MASTERCHAIN_SEQNO);
    assertNotNull(response);
  }

  @Test
  public void testGetBlockchainConfig() {
    BlockchainConfig response = tonapi.getBlockchain().getBlockchainConfig(MASTERCHAIN_SEQNO);
    assertNotNull(response);
  }

  @Test
  public void testGetRawBlockchainConfig() {
    RawBlockchainConfig response = tonapi.getBlockchain().getRawBlockchainConfig(MASTERCHAIN_SEQNO);
    assertNotNull(response);
  }

  @Test
  public void testGetTransactionFromBlock() {
    Transactions response = tonapi.getBlockchain().getTransactionFromBlock(BLOCK_ID);
    assertNotNull(response);
  }

  @Test
  public void testGetTransactionData() {
    Transaction response = tonapi.getBlockchain().getTransactionData(TRANSACTION_ID);
    assertNotNull(response);
  }

  @Test
  @Disabled
  public void testGetTransactionByMessage() {
    Transaction response = tonapi.getBlockchain().getTransactionByMessage(MESSAGE_ID);
    assertNotNull(response);
  }

  @Test
  public void testGetValidators() {
    Validators response = tonapi.getBlockchain().getValidators();
    assertNotNull(response);
  }

  @Test
  public void testGetLastMasterchainBlock() {
    BlockchainBlock response = tonapi.getBlockchain().getLastMasterchainBlock();
    assertNotNull(response);
  }

  @Test
  public void testGetAccountInfo() {
    BlockchainRawAccount response = tonapi.getBlockchain().getAccountInfo(ACCOUNT_ID);
    assertNotNull(response);
  }

  @Test
  public void testGetAccountTransactions() {
    Transactions response = tonapi.getBlockchain()
        .getAccountTransactions(ACCOUNT_ID, AFTER_LT, BEFORE_LT, LIMIT);
    assertNotNull(response);
  }

  @Test
  public void testInspectAccount() {
    BlockchainAccountInspect response = tonapi.getBlockchain().inspectAccount(ACCOUNT_ID);
    assertNotNull(response);
  }

  @Test
  public void testGetConfig() {
    BlockchainConfig response = tonapi.getBlockchain().getConfig();
    assertNotNull(response);
  }

  @Test
  public void testGetRawConfig() {
    RawBlockchainConfig response = tonapi.getBlockchain().getRawConfig();
    assertNotNull(response);
  }

  @Test
  public void testExecuteGetMethod() {
    MethodExecutionResult response = tonapi.getBlockchain()
        .executeGetMethod(ACCOUNT_ID_NFT, "get_nft_data");
    assertNotNull(response);
  }
}
