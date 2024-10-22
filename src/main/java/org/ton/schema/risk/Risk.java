package org.ton.schema.risk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.jettons.JettonQuantity;
import org.ton.schema.nft.NftItem;

import java.util.List;


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
