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
public class TvmStackRecord {
    private String type;
    private String cell;
    private String slice;
    private String num;
    private List<TvmStackRecord> tuple;
}
