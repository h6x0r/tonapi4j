package org.ton.schema.nft;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.Address;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Collection {
    private Address address;
    private String name;
    private String description;
}
