package org.ton.schema.dns;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DNSRecord {

  private WalletDNS wallet;
  private String nextResolver;
  private List<String> sites;
  private String storage;
}
