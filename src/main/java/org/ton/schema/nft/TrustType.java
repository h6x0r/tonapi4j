package org.ton.schema.nft;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TrustType {
  WHITELIST("whitelist"),
  GRAYLIST("graylist"),
  BLACKLIST("blacklist"),
  NONE("none");

  private final String value;

  TrustType(String value) {
    this.value = value;
  }

  @JsonCreator
  public static TrustType fromValue(String value) {
    for (TrustType type : TrustType.values()) {
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
