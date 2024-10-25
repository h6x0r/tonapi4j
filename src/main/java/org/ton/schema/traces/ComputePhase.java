package org.ton.schema.traces;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComputePhase {

  private Boolean skipped;
  private ComputeSkipReason skipReason;
  private Boolean success;
  private Long gasFees;
  private Long gasUsed;
  private Long vmSteps;
  private Long exitCode;
  private String exitCodeDescription;
}
