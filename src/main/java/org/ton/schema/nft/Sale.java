package org.ton.schema.nft;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.Address;
import org.ton.schema.accounts.AccountAddress;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sale {
    private Address address;
    private AccountAddress market;
    private AccountAddress owner;
    private Price price;
}
