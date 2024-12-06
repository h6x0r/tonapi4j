package org.ton.schema.events.action;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.accounts.AccountAddress;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TonTransferAction {

  private AccountAddress sender;
  private AccountAddress recipient;
  private Long amount;
  private String comment;
  private EncryptedComment encryptedComment;
  private Refund refund;
}
