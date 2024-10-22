package org.ton.schema.jettons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JettonTransferPayload {
    private String customPayload;
    private String stateInit;
}
