package org.ton.schema.stacking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.Address;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PoolInfo {

  private Address address;
  private String name;
  private Long totalAmount;
  private String implementation;
  private Double apy;
  private Long minStake;
  private Long cycleStart;
  private Long cycleEnd;
  private Long cycleLength;
  private Boolean verified;
  private Long currentNominators;
  private Long maxNominators;
  private String liquidJettonMaster;
  private Long nominatorsStake;
  private Long validatorStake;
}
