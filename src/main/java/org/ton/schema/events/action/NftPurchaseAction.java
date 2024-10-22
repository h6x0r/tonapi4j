package org.ton.schema.events.action;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.accounts.AccountAddress;
import org.ton.schema.nft.NftItem;
import org.ton.schema.nft.Price;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NftPurchaseAction {
    private String auctionType;
    private Price amount;
    private NftItem nft;
    private AccountAddress seller;
    private AccountAddress buyer;
}
