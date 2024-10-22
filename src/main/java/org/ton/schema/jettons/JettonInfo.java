package org.ton.schema.jettons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.accounts.AccountAddress;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JettonInfo {
    private Boolean mintable;
    private String totalSupply;
    private AccountAddress admin;
    private JettonMetadata metadata;
    private JettonVerificationType verification;
    private Long holdersCount;
}
