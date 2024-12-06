package org.ton.schema.liteserver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawMasterChainInfo {

  private BlockRaw last;
  private String stateRootHash;
  private InitStateRaw init;
}
