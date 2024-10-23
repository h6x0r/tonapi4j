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
public class InscriptionMintAction {
    private AccountAddress recipient;
    private BigInteger amount;
    private String type;
    private String ticker;
    private Integer decimals;
}
