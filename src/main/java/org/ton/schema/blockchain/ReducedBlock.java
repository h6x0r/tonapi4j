package org.ton.schema.blockchain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReducedBlock {
    private Long workchainId;
    private String shard;
    private Long seqno;
    private String masterRef;
    private Long txQuantity;
    private Long utime;
    private List<String> shardsBlocks;
    private List<String> parent;
}
