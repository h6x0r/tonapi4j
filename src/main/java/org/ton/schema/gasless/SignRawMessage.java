package org.ton.schema.gasless;

import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignRawMessage {

  private String address;
  private BigInteger amount;
  private String payload;
  private String stateInit;
}
