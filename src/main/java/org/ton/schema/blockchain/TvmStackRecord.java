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
public class TvmStackRecord {

  private String type;
  private String cell;
  private String slice;
  private String num;
  private List<TvmStackRecord> tuple;
}
