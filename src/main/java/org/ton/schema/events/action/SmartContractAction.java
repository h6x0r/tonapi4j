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
public class SmartContractAction {

  private AccountAddress executor;
  private AccountAddress contract;
  private Long tonAttached;
  private String operation;
  private String payload;
  private Refund refund;
}
