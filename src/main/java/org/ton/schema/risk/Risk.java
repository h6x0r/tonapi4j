package org.ton.schema.risk;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.jettons.JettonQuantity;
import org.ton.schema.nft.NftItem;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Risk {

  private String description;
  private Boolean transferAllRemainingBalance;
  private Long ton;
  private List<JettonQuantity> jettons;
  private List<NftItem> nfts;
}
