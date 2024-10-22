package org.ton.schema.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.Address;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEventData {
    private Address accountId;
    private Long lt;
    private String txHash;
}
