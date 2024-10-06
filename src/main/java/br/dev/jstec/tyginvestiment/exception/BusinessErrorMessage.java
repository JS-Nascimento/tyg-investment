package br.dev.jstec.tyginvestiment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessErrorMessage {
    USER_NOT_FOUND(1, "Usuário não encontrado"),
    EMAIL_ALREADY_EXISTS(2, "Email já cadastrado"),
    CATEGORY_PARENT_NOT_FOUND(3, "Categoria Pai não encontrada"),
    CATEGORY_NOT_FOUND(4, "Categoria não encontrada"),
    COMPETENCE_PERIOD_NOT_FOUND(5, "Período de competência não encontrado"),
    COMPETENCE_PERIOD_ALREADY_CONSOLIDATED(6, "Período de competência já consolidado"),
    COMPETENCE_PERIOD_NOT_CONSOLIDATED(7, "Períodos de competência não consolidados"),
    COMPETENCE_PERIOD_ALREADY_EXISTS(8, "Período de competência já cadastrado neste Planejamento Financeiro"),
    COMPETENCE_PERIOD_INVALID(9, "Período de competência inválido, o período de competência deve ser maior que o último período cadastrado em 1 mês"),
    FINANCIAL_PLANNING_NOT_FOUND(10, "Planejamento Financeiro não encontrado"),
    FINANCIAL_PLANNING_ALREADY_CONSOLIDATED(11, "Planejamento Financeiro já consolidado"),
    CATEGORY_PLANNING_NOT_FOUND(12, "Categoria de Planejamento não encontrada"),
    CATEGORY_PLANNING_ALREADY_EXISTS(13, "Categoria de Planejamento já cadastrada"),
    CATEGORY_ALREADY_EXISTS(14, "Categoria já cadastrada para esse período de competência"),
    CATEGORY_PLANNING_NOT_VALID_TO_SUBCATEGORY(15, "Categoria de Planejamento não é válida para subcategoria"),
    CATEGORY_PARENT_REQUIRED(16, "Categoria Pai é obrigatória para subcategoria"),
    SUBCATEGORY_NOT_MIGRATE_TO_CATEGORY_PARENT(17, "Subcategoria não pode ser migrada para categoria pai"),
    CATEGORY_PLANNING_NOT_SUBCATEGORY(18, "Categoria de Planejamento não é subcategoria."),
    CHECKING_ACCOUNT_NOT_FOUND(19, "Conta Corrente não encontrada"),
    STATEMENT_NOT_FOUND(20, "Lançamento não encontrado"),
    ERROR_CREATING_USER(21, "Ocorreu um erro ao criar o usuário, por favor tente novamente."),
    USER_ALREADY_EXISTS(22, "Existe um usuário cadastrado com esses dados."),
    EXIST_FINANCIAL_PLANNING_NOT_CONSOLIDATED(23, "Já existe um planejamento financeiro não consolidado."),
    CATEGORY_NOT_FOUND_IN_COMPETENCE_PERIOD(24, "Categoria não encontrada no período de competência."),
    CATEGORY_ALREADY_CONSOLIDATED(25, "Categoria já consolidada."),
    CATEGORY_NOT_HAVE_STATEMENTS_ATTACHED(26, "Categoria possui lançamentos vinculados."),
    FINAL_DATE_MUST_BE_LATER_THAN_STAR_DATE(27, "Data final deve ser posterior a data inicial."),
    BUDGET_CANNOT_BE_NEGATIVE_OR_ZERO(28, "Orçamento não pode ser negativo ou zero."),
    CATEGORY_HAS_STATEMENTS_ATTACHED(29, "Categoria possui lançamentos vinculados. Não é possível modificar o tipo."),
    STATEMENT_ALREADY_CONSOLIDATED(30, "Lançamento já consolidado."),
    STATEMENT_NOT_CONSOLIDATED(31, "Lançamento não consolidado."),
    ;

    private final int code;
    private final String message;
}
