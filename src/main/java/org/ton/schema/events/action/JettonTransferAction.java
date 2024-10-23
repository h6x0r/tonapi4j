package org.ton.schema.events.action;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.Address;
import org.ton.schema.accounts.AccountAddress;
import org.ton.schema.jettons.JettonPreview;

import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JettonTransferAction {
    private AccountAddress sender;
    private AccountAddress recipient;
    private Address sendersWallet;
    private String recipientsWallet;
    private BigInteger amount;
    private String comment;
    private EncryptedComment encryptedComment;
    private Refund refund;
    private JettonPreview jetton;
}
