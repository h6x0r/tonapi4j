package org.ton.schema.nft;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.Address;
import org.ton.schema.accounts.AccountAddress;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NftCollection {
    private Address address;
    private Long nextItemIndex;
    private AccountAddress owner;
    private String rawCollectionContent;
    private Map<String, Object> metadata;
    private List<ImagePreview> previews;
    private List<String> approvedBy; // getgems, tonkeeper, ton.diamonds, none
}
