package org.ton.schema.multisig;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.Address;
import org.ton.schema.risk.Risk;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MultisigOrder {
    private Address address;
    private Long orderSeqno;
    private Long threshold;
    private boolean sentForExecution;
    private List<Address> signers;
    private Long approvalsNum;
    private Long expirationData;
    private Risk risk;
}
