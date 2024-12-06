package org.ton.schema.stacking;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StakingPools {

  private List<PoolInfo> pools;
  private Map<String, PoolImplementation> implementations;
}
