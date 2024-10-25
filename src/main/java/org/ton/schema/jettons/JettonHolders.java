package org.ton.schema.jettons;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JettonHolders {

  private List<JettonHolder> addresses;
  private Long total;
}
