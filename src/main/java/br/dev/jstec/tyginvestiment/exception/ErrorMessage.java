package br.dev.jstec.tyginvestiment.exception;

import lombok.Getter;

@Getter
public enum ErrorMessage {

    USER_UNAUTHORIZED(101, "Usuário não autorizado"),
    USER_NOT_FOUND(102, "Usuário não encontrado"),
    USER_ALREADY_EXISTS(103, "Usuário já existe"),
    USER_INVALID_INFORMATION(104, "Informação inválida"),
    TENANT_NOT_FOUND(105, "Usuário não informado."),
    INVALID_TOKEN(106, "Token inválido."),
    USER_EMAIL_NOT_VERIFIED(107, "Por favor verifique seu email antes de continuar."),
    API_LIMIT_REACHED(108, "Limite de requisições atingido para {0} "),
    ALPHA_VANTAGE_REQUEST_ERROR(109, "Erro ao buscar informações em Alpha Vantage. Com a mensagem: {0}"),
    ASSET_NOT_FOUND(110, "Ativo não encontrado com o símbolo: {0}"),
    ATTRIBUTE_NOT_FOUND(111, "Atributo {0} é obrigatório."),
    INVALID_CONVERSION_RATE(112, "Taxa de conversão inválida para a moeda {0}"),
    ACCOUNT_NOT_FOUND(113, "Conta não encontrada com o id: {0}"),
    ASSET_INVALID_INFORMATION(114, "Informação inválida para o ativo."),
    ;

    private final int code;
    private final String message;

    ErrorMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
