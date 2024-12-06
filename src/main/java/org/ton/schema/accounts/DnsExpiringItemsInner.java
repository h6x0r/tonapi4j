package org.ton.schema.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.nft.NftItem;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DnsExpiringItemsInner {

  private Long expiringAt;
  private String name;
  private NftItem dnsItem;
}
