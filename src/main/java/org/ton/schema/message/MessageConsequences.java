package org.ton.schema.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.events.AccountEvent;
import org.ton.schema.risk.Risk;
import org.ton.schema.traces.Trace;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageConsequences {

  private Trace trace;
  private Risk risk;
  private AccountEvent event;
}
