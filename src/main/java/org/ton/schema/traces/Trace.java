package org.ton.schema.traces;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.transactions.Transaction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trace {

  private Transaction transaction;
  private List<String> interfaces;
  private List<Trace> children;
  private Boolean emulated;
}
