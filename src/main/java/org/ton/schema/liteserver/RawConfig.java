package org.ton.schema.liteserver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawConfig {
    private Integer mode;
    private BlockRaw id;
    private String stateProof;
    private String configProof;
}
