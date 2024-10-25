package org.ton.schema.wallet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Proof {

  private Long timestamp;
  private Domain domain;
  private String signature;
  private String payload;
  private String stateInit;
}
