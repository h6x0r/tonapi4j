package org.ton.schema.jettons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.Address;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JettonPreview {

  private Address address;
  private String name;
  private String symbol;
  private Integer decimals;
  private String image;
  private JettonVerificationType verification;
}
