package org.ton.schema.blockchain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.accounts.AccountAddress;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DecodedMessage {
    private AccountAddress destination;
    private String destinationWalletVersion;
    private Map<String, Object> extInMsgDecoded;
}
