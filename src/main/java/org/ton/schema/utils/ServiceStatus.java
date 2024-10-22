package org.ton.schema.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceStatus {
    private Boolean restOnline;
    private Long indexingLatency;
    private Boolean lastKnownMasterchainSeqno;
}
