package org.ton.schema.blockchain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidatorSet {

  private Long total;
  private Long main;
  private List<ValidatorSetNode> list;
  private Long totalWeight;
  private Long utimeSince;
  private Long utimeUntil;
}
