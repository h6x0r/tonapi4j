package org.ton.schema.storage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.Address;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorageProvider {

  private Address address;
  private Boolean acceptNewContracts;
  private Long ratePerMbDay;
  private Long maxSpan;
  private Long minimalFileSize;
  private Long maximalFileSize;
}
