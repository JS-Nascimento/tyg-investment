package br.dev.jstec.tyginvestiment.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final int code;

    public BusinessException(BusinessErrorMessage message) {
        super(message.getMessage());
        this.code = message.getCode();
    }

    public BusinessException(BusinessErrorMessage message, String... messageArguments) {
        super(formatErrorMessage(message, messageArguments));
        this.code = message.getCode();
    }

    private static String formatErrorMessage(BusinessErrorMessage message, String... messageArguments) {
        return String.format(message.getMessage(), messageArguments);
    }
}

