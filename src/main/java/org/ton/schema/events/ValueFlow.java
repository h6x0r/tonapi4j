package org.ton.schema.events;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.accounts.AccountAddress;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValueFlow {

  private AccountAddress account;
  private Long ton;
  private Long fees;
  private List<ValueFlowJettonsInner> jettons;
}
