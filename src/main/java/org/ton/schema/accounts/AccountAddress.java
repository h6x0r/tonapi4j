package org.ton.schema.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.Address;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountAddress {
    private Address address;
    private String name;
    private String icon;
    private Boolean isScam;
    private Boolean isWallet;
}
