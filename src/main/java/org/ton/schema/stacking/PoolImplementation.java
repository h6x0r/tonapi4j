package org.ton.schema.stacking;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
