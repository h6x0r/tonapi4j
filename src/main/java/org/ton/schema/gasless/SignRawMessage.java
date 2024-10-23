package org.ton.schema.gasless;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignRawMessage {
    private String address;
    private BigInteger amount;
    private String payload;
    private String stateInit;
}
