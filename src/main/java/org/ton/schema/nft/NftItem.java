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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NftItem {
    private Address address;
    private Long index;
    private AccountAddress owner;
    private Collection collection;
    private boolean verified;
    private Map<String, Object> metadata;
    private Sale sale;
    private List<ImagePreview> previews;
    private String dns;
    private Boolean includeCnft;
    private TrustType trust;
    private List<String> approvedBy;
}
