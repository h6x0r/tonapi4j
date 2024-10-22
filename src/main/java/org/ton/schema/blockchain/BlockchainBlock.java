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
public class BlockchainBlock {
    private Long txQuantity;
    private BlockValueFlow valueFlow;
    private Long workchainId;
    private String shard;
    private Long seqno;
    private String rootHash;
    private String fileHash;
    private Long globalId;
    private Long version;
    private Boolean afterMerge;
    private Boolean beforeSplit;
    private Boolean afterSplit;
    private Boolean wantSplit;
    private Boolean wantMerge;
    private Boolean keyBlock;
    private Long genUtime;
    private Long startLt;
    private Long endLt;
    private Long vertSeqno;
    private Long genCatchainSeqno;
    private Long minRefMcSeqno;
    private Long prevKeyBlockSeqno;
    private Long genSoftwareVersion;
    private Long genSoftwareCapabilities;
    private String masterRef;
    private List<String> prevRefs;
    private Long inMsgDescrLength;
    private Long outMsgDescrLength;
    private String randSeed;
    private String createdBy;
}
