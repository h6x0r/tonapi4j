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
public class RawBlockProof {
    private Boolean complete;
    private BlockRaw from;
    private BlockRaw to;
    private List<Object> steps;
}
