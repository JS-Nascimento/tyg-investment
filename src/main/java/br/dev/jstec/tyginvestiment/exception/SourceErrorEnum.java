package br.dev.jstec.tyginvestiment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SourceErrorEnum {
    INFRASTRUCTURE("Infrastructure Layer"),
    APPLICATION("Business Layer"),
    DOMAIN("Domain Layer"),
    ;

    private final String source;
}
