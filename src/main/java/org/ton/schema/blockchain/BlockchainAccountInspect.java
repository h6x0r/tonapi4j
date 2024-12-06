package org.ton.schema.blockchain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlockchainAccountInspect {

  private String code;
  private String codeHash;
  private List<BlockchainAccountInspectMethodsInner> methods;
  private String compiler;
}
