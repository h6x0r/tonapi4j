package org.ton.schema.gasless;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GaslessConfig {

  private String relayAddress;
  private List<GaslessJetton> gasJettons;
}
