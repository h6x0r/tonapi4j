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
public class Validators {

  private long electAt;
  private long electClose;
  private Long minStake;
  private Long totalStake;
  private List<Validator> validators;
}
