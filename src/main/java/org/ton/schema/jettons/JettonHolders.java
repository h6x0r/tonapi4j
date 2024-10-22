package org.ton.schema.jettons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JettonHolders {
    private List<JettonHolder> addresses;
    private Long total;
}
