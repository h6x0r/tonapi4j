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
public class InscriptionMintAction {
    private AccountAddress recipient;
    private String amount;
    private String type;
    private String ticker;
    private Integer decimals;
}