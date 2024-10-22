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
public class JettonSwapAction {
    private String dex;
    private String amountIn;
    private String amountOut;
    private Integer tonIn;
    private Integer tonOut;
    private AccountAddress userWallet;
    private AccountAddress router;
    private JettonPreview jettonMasterIn;
    private JettonPreview jettonMasterOut;
}