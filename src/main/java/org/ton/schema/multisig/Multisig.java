package org.ton.schema.multisig;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.Address;

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
