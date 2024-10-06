package br.dev.jstec.tyginvestiment.exception;

import lombok.Getter;

@Getter
public enum BaseErrorMessage {

    INVALID_INFORMATION_ERROR(1001, "Informação inválida"),
    MISSING_MANDATORY_PARAMETER(1002, "Atributo obrigatório: {0}"),
    REQUEST_ERROR(1003, "Erro na requisição: {0}"),
    ERRO_INTERNO(1004, "Erro interno: {0}"),
    ERRO_NAO_ENCONTRADO(1005, "Registro não encontrado: {0}"),
    ERRO_ACESSO_NEGADO(1006, "Acesso negado: {0}"),
    ERRO_NAO_AUTORIZADO(1007, "Não autorizado: {0}"),
    ERRO_NAO_IMPLEMENTADO(1008, "Não implementado: {0}"),
    ERRO_SERVICO_INDISPONIVEL(1009, "Serviço indisponível: {0}"),
    ERRO_SERVICO_NAO_ENCONTRADO(1010, "Serviço não encontrado: {0}"),
    ERRO_SERVICO_NAO_AUTORIZADO(1011, "Serviço não autorizado: {0}"),
    ERRO_SERVICO_NAO_IMPLEMENTADO(1012, "Serviço não implementado: {0}"),
    ERRO_SERVICO_INTERNO(1013, "Erro interno no serviço: {0}"),
    ERRO_SERVICO_NAO_AUTENTICADO(1014, "Serviço não autenticado: {0}"),

    ;

    private final int code;
    private final String msg;

    BaseErrorMessage(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
