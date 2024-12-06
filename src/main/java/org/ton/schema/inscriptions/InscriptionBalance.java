package org.ton.schema.inscriptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InscriptionBalance {

  private String type;
  private String ticker;
  private String balance;
  private Integer decimals;
}
