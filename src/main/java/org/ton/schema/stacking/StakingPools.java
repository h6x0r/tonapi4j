package org.ton.schema.stacking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StakingPools {
    private List<PoolInfo> pools;
    private Map<String, PoolImplementation> implementations;
}
