package org.ton.schema.blockchain;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.accounts.AccountAddress;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DecodedMessage {

  private AccountAddress destination;
  private String destinationWalletVersion;
  private Map<String, Object> extInMsgDecoded;
}
