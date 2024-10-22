package org.ton.schema.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.accounts.AccountAddress;
import org.ton.schema.traces.StateInit;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    private String msgType;
    private Long createdLt;
    private boolean ihrDisabled;
    private boolean bounce;
    private boolean bounced;
    private Long value;
    private Long fwdFee;
    private Long ihrFee;
    private AccountAddress destination;
    private AccountAddress source;
    private Long importFee;
    private Long createdAt;
    private String opCode;
    private StateInit init;
    private String hash;
    private String rawBody;
    private String decodedOpName;
    private Map<String, Object> decodedBody;
}
