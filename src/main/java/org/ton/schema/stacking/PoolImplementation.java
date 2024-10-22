package org.ton.schema.stacking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PoolImplementation {
    private String name;
    private String description;
    private String url;
    private List<String> socials;
}
