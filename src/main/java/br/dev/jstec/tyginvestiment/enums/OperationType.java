package br.dev.jstec.tyginvestiment.enums;

import br.dev.jstec.tyginvestiment.exception.BusinessException;
import lombok.Getter;

import static br.dev.jstec.tyginvestiment.exception.BusinessErrorMessage.INVALID_OPERATION_TYPE;

@Getter
public enum OperationType {

    CREDIT,
    DEBIT;

    public static OperationType fromString(String value) {
        for (OperationType operationType : OperationType.values()) {
            if (operationType.name().equalsIgnoreCase(value)) {
                return operationType;
            }
        }
        throw new BusinessException(INVALID_OPERATION_TYPE);
    }
}
