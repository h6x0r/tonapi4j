package org.ton.schema.dns;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
