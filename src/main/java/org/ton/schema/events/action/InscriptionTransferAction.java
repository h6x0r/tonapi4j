package org.ton.schema.events.action;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.accounts.AccountAddress;

import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InscriptionTransferAction {
    private AccountAddress sender;
    private AccountAddress recipient;
    private BigInteger amount;
    private String comment;
    private String type;
    private String ticker;
    private Integer decimals;
}
