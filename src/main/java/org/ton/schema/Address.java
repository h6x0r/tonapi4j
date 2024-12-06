package org.ton.schema;

import lombok.Value;
import org.ton.util.Utils;

@Value
public class Address {

  String value;

  @Override
  public String toString() {
    return value;
  }

  public String toRaw() {
    return value;
  }

  public String toUserFriendly(boolean isBounceable) {
    return Utils.rawToUserFriendly(value, isBounceable);
  }
}
