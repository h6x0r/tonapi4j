package org.ton.schema.traces;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActionPhase {
    private Boolean success;
    private Long resultCode;
    private Long totalActions;
    private Long skippedActions;
    private Long fwdFees;
    private Long totalFees;
    private String resultCodeDescription;
}
