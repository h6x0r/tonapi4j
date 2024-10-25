package org.ton.schema.liteserver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawMasterChainInfoExt {

  private Integer mode;
  private Long version;
  private Long capabilities;
  private BlockRaw last;
  private Long lastUtime;
  private Long now;
  private String stateRootHash;
  private InitStateRaw init;
}
