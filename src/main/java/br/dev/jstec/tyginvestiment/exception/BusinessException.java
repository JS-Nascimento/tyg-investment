package br.dev.jstec.tyginvestiment.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final int code;

    public BusinessException(BusinessErrorMessage message) {
        super(message.getMessage());
        this.code = message.getCode();
    }
}

