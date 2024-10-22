package org.ton.schema.liteserver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawBlockTransactionID {
    private Integer mode;
    private String account;
    private Long lt;
    private String hash;
}
