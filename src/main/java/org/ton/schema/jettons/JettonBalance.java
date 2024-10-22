package org.ton.schema.jettons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.accounts.AccountAddress;
import org.ton.schema.rates.TokenRates;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JettonBalance {
    private String balance;
    private TokenRates price;
    private AccountAddress walletAddress;
    private JettonPreview jetton;
}
