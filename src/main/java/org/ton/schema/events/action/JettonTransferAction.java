package org.ton.schema.events.action;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.accounts.AccountAddress;
import org.ton.schema.jettons.JettonPreview;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JettonTransferAction {
    private AccountAddress sender;
    private AccountAddress recipient;
    private String sendersWallet;
    private String recipientsWallet;
    private String amount;
    private String comment;
    private EncryptedComment encryptedComment;
    private Refund refund;
    private JettonPreview jetton;
}
