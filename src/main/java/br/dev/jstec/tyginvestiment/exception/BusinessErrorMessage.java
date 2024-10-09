package br.dev.jstec.tyginvestiment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessErrorMessage {
    USER_NOT_FOUND(1, "Usuário não encontrado"),
    EMAIL_ALREADY_EXISTS(2, "Email já cadastrado"),
    TRANSACTION_TYPE_REQUIRED(3, "Tipo de transação é obrigatório"),
    ASSET_ID_REQUIRED(4, "Ativo é obrigatório"),
    ACCOUNT_ID_REQUIRED(5, "Conta é obrigatória"),
    QUANTITY_REQUIRED(6, "Quantidade negociada é obrigatória"),
    VALUE_REQUIRED(7, "Valor da transação é obrigatório"),
    INSUFFICIENT_FUNDS(8, "Saldo insuficiente para realizar a transação"),
    ;

    private final int code;
    private final String message;
}
