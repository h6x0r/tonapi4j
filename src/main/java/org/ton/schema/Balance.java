package org.ton.schema;

import lombok.Value;
import org.ton.util.Utils;

@Value
public class Balance {

  Long nanotonValue;

  @Override
  public String toString() {
    return Long.toString(nanotonValue);
  }

  public long toNano(int decimals) {
    return nanotonValue;
  }

  public Number toAmount(int decimals, int precision) {
    return Utils.toAmount(nanotonValue, decimals, precision);
  }
}
