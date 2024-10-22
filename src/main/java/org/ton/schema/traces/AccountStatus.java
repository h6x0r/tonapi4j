package org.ton.schema.traces;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AccountStatus {
    NONEXIST("nonexist"),
    UNINIT("uninit"),
    ACTIVE("active"),
    FROZEN("frozen");

    private final String value;

    AccountStatus(String value) {
        this.value = value;
    }

    @JsonCreator
    public static AccountStatus fromValue(String value) {
        for (AccountStatus status : AccountStatus.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown AccountStatus: " + value);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
