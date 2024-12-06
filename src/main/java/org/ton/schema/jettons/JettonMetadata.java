package org.ton.schema.jettons;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.Address;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JettonMetadata {

  private Address address;
  private String name;
  private String symbol;
  private String decimals;
  private String image;
  private String description;
  private List<String> social;
  private List<String> websites;
  private List<String> catalogs;
}
