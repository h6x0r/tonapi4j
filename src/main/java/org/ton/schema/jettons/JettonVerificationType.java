package org.ton.schema.jettons;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum JettonVerificationType {
  WHITELIST("whitelist"),
  GRAYLIST("graylist"),
  BLACKLIST("blacklist"),
  NONE("none");

  private final String value;

  JettonVerificationType(String value) {
    this.value = value;
  }

  @JsonCreator
  public static JettonVerificationType fromValue(String value) {
    for (JettonVerificationType type : JettonVerificationType.values()) {
      if (type.value.equalsIgnoreCase(value)) {
        return type;
      }
    }
    return NONE;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
