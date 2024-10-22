package org.ton.schema.liteserver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawShardProof {
    private BlockRaw masterchainId;
    private List<LinkedHashMap<String, Object>> links;
}
