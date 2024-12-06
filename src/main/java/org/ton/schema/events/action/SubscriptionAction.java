package org.ton.schema.events.action;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.accounts.AccountAddress;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionAction {

  private AccountAddress subscriber;
  private String subscription;
  private AccountAddress beneficiary;
  private Long amount;
  private Boolean initial;
}
