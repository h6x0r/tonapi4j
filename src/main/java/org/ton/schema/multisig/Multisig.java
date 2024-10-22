package org.ton.schema.multisig;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.Address;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Multisig {
    private Address address;
    private Long seqno;
    private Long threshold;
    private List<Address> signers;
    private List<Address> proposers;
    private MultisigOrder orders;
}
