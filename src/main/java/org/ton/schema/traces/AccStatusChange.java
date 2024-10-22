package org.ton.schema.traces;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AccStatusChange {
    ACST_UNCHANGED("acst_unchanged"),
    ACST_FROZEN("acst_frozen"),
    ACST_DELETED("acst_deleted");

    private final String value;

    AccStatusChange(String value) {
        this.value = value;
    }

    @JsonCreator
    public static AccStatusChange fromValue(String value) {
        for (AccStatusChange status : AccStatusChange.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown AccStatusChange: " + value);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
