package org.ton.schema.liteserver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawAccountState {
    private BlockRaw id;
    private BlockRaw shardblk;
    private String shardProof;
    private String proof;
    private String state;
}
