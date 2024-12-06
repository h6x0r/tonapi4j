package org.ton.schema.blockchain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValueFlowItem {

  private Long grams;
  private List<OtherValue> other;
}
