package org.ton.schema.traces;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BouncePhaseType {
  TR_PHASE_BOUNCE_NEGFUNDS("TrPhaseBounceNegfunds"),
  TR_PHASE_BOUNCE_NOFUNDS("TrPhaseBounceNofunds"),
  TR_PHASE_BOUNCE_OK("TrPhaseBounceOk");

  private final String value;

  BouncePhaseType(String value) {
    this.value = value;
  }

  @JsonCreator
  public static BouncePhaseType fromValue(String value) {
    for (BouncePhaseType type : BouncePhaseType.values()) {
      if (type.value.equalsIgnoreCase(value)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Unknown BouncePhaseType: " + value);
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
