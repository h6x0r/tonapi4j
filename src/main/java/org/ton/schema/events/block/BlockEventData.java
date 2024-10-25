package org.ton.schema.events.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlockEventData {

  private Long workchain;
  private String shard;
  private Long seqno;
  private String rootHash;
  private String fileHash;
}
