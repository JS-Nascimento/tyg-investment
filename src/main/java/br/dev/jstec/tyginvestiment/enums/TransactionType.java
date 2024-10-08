package br.dev.jstec.tyginvestiment.enums;

import lombok.Getter;

@Getter
public enum TransactionType {
    BUY,
    SELL,
    DIVIDEND,
    OTHER;

    public static TransactionType fromString(String value) {
        for (TransactionType type : TransactionType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        return null;
    }
}
