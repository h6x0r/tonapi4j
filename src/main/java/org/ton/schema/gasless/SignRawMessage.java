package org.ton.schema.gasless;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignRawMessage {
    private String address;
    private String amount;
    private String payload;
    private String stateInit;
}
