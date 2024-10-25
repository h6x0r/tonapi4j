package org.ton.schema.liteserver;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
