package org.ton.schema.liteserver;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutMsgQueueSize {

  private Long extMsgQueueSizeLimit;
  private List<OutMsgQueueShard> shards;
}
