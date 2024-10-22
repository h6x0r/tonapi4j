package org.ton.schema.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.Balance;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceChange {
    private Balance balanceChange;
}
