package org.ton.schema.liteserver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutMsgQueueSize {
    private Long extMsgQueueSizeLimit;
    private List<OutMsgQueueShard> shards;
}
