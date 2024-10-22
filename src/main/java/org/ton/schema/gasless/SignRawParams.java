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
public class SignRawParams {
    private String relayAddress;
    private String commission;
    private String from;
    private Long validUntil;
    private List<SignRawMessage> messages;
}
