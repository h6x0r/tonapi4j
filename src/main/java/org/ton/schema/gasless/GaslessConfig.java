package org.ton.schema.gasless;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GaslessConfig {
    private String relayAddress;
    private List<GaslessJetton> gasJettons;
}
