package org.ton.schema.stacking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountStakingInfo {

  private String pool;
  private Long amount;
  private Long pendingDeposit;
  private Long pendingWithdraw;
  private Long readyWithdraw;
}
