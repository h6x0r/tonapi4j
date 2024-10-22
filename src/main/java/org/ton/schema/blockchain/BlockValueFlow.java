package org.ton.schema.blockchain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlockValueFlow {
    private ValueFlowItem fromPrevBlk;
    private ValueFlowItem toNextBlk;
    private ValueFlowItem imported;
    private ValueFlowItem exported;
    private ValueFlowItem feesCollected;
    private ValueFlowItem burned;
    private ValueFlowItem feesImported;
    private ValueFlowItem recovered;
    private ValueFlowItem created;
    private ValueFlowItem minted;
}
