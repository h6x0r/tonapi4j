package org.ton.schema.blockchain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountStorageInfo {

  private Long usedCells;
  private Long usedBits;
  private Long usedPublicCells;
  private Long lastPaid;
  private Long duePayment;
}
