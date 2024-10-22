package org.ton.schema.blockchain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.Address;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Validator {
    private Address address;
    private String adnlAddress;
    private Long stake;
    private Long maxFactor;
}
