package org.ton.schema.traces;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TransactionType {
    TRANS_ORD("TransOrd"),
    TRANS_TICK_TOCK("TransTickTock"),
    TRANS_SPLIT_PREPARE("TransSplitPrepare"),
    TRANS_SPLIT_INSTALL("TransSplitInstall"),
    TRANS_MERGE_PREPARE("TransMergePrepare"),
    TRANS_MERGE_INSTALL("TransMergeInstall"),
    TRANS_STORAGE("TransStorage");

    private final String value;

    TransactionType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static TransactionType fromValue(String value) {
        for (TransactionType type : TransactionType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown TransactionType: " + value);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
