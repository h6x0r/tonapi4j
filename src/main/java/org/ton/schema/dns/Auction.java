package org.ton.schema.dns;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Auction {

  private String domain;
  private String owner;
  private Long price;
  private Long bids;
  private Long date;
}
