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
public class ValidatorSet {
    private Long total;
    private Long main;
    private List<ValidatorSetNode> list;
    private Long totalWeight;
    private Long utimeSince;
    private Long utimeUntil;
}
