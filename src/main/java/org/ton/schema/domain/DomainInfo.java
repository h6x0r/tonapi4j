package org.ton.schema.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.nft.NftItem;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DomainInfo {

  private String name;
  private Integer expiringAt;
  private NftItem item;
}
