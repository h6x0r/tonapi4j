package org.ton.schema.blockchain;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MethodExecutionResult {

  private boolean success;
  private int exitCode;

  private List<TvmStackRecord> stack;
  private Map<String, Object> decoded;
}
