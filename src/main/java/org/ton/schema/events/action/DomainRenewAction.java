package org.ton.schema.events.action;

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
public class DomainRenewAction {
    private String domain;
    private Address contractAddress;
    private AccountAddress renewer;
}
