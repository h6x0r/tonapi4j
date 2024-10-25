package org.ton.schema.blockchain;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.Address;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlockchainRawAccount {

  private Address address;
  private Long balance;
  private Map<String, String> extraBalance;
  private String code;
  private String data;
  private Long lastTransactionLt;
  private String lastTransactionHash;
  private String status;
  private AccountStorageInfo storage;
}
