package org.ton.schema.liteserver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlockRaw {

  private Long workchain;
  private String shard;
  private Long seqno;
  private String rootHash;
  private String fileHash;
}
