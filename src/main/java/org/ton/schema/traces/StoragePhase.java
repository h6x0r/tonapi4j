package org.ton.schema.traces;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoragePhase {
    private Long feesCollected;
    private Long feesDue;
    private AccStatusChange statusChange;
}
