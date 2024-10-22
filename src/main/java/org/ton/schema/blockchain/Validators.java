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
public class Validators {
    private long electAt;
    private long electClose;
    private Long minStake;
    private Long totalStake;
    private List<Validator> validators;
}
