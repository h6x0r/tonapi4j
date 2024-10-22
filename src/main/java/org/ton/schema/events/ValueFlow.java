package org.ton.schema.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.accounts.AccountAddress;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValueFlow {
    private AccountAddress account;
    private Long ton;
    private Long fees;
    private List<ValueFlowJettonsInner> jettons;
}
