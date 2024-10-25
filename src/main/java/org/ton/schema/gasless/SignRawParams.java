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
public class SignRawParams {

  private String relayAddress;
  private String commission;
  private String from;
  private Long validUntil;
  private List<SignRawMessage> messages;
}
