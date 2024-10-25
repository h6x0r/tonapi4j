package org.ton.schema.traces;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ComputeSkipReason {
  CSKIP_NO_STATE("cskip_no_state"),
  CSKIP_BAD_STATE("cskip_bad_state"),
  CSKIP_NO_GAS("cskip_no_gas");

  private final String value;

  ComputeSkipReason(String value) {
    this.value = value;
  }

  @JsonCreator
  public static ComputeSkipReason fromValue(String value) {
    for (ComputeSkipReason reason : ComputeSkipReason.values()) {
      if (reason.value.equalsIgnoreCase(value)) {
        return reason;
      }
    }
    throw new IllegalArgumentException("Unknown ComputeSkipReason: " + value);
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
