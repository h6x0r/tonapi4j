package org.ton.schema.liteserver;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawListBlockTransactions {

  private BlockRaw id;
  private Long reqCount;
  private Boolean incomplete;
  private List<RawBlockTransactionID> ids;
  private String proof;
}
