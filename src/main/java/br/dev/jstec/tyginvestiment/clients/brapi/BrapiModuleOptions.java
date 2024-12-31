package br.dev.jstec.tyginvestiment.clients.brapi;

import br.dev.jstec.tyginvestiment.exception.InfrastructureException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static br.dev.jstec.tyginvestiment.exception.ErrorMessage.BRAPI_INTERVAL_NOT_FOUND;

@AllArgsConstructor
@Getter
public enum BrapiModuleOptions {

    SUMMARY("summaryProfile ", "Perfil resumido", "Resumo da empresa: O resumo da " +
            "empresa fornece uma descrição geral da empresa, incluindo o setor em que ela opera, " +
            "o número de funcionários, o endereço da sede, o site, etc."),
    BALANCE_SHEET("balanceSheetHistory", "Balanço Patrimonial", "Balanço Patrimonial Histórico:" +
            " O balanço patrimonial é um dos principais demonstrativos financeiros de uma empresa " +
            "que mostra o que a empresa possui (ativos), o que ela deve (passivos) e o que sobra para os acionistas " +
            "(patrimônio líquido) em uma determinada data."),
    BALANCE_SHEET_QUARTERLY("balanceSheetHistoryQuarterly", "Balanço Patrimonial Trimestral",
            "Balanço Patrimonial Trimestral:" +
                    " O balanço patrimonial é um dos principais demonstrativos financeiros de uma empresa " +
                    "que mostra o que a empresa possui (ativos), o que ela deve (passivos) e o que sobra para os acionistas " +
                    "(patrimônio líquido) em uma determinada data."),
    DEFAULT_KEY_STATISTICS("defaultKeyStatistics", "Principais Estatísticas da Empresa",
            "Esse módulo mostra" +
                    " as principais estatísticas da empresa, como a quantidade de cotas em circulação, o beta," +
                    " a margem de lucro, etc."),
    INCOME_STATEMENT("incomeStatementHistory", "Demonstrativo de Resultados",
            "Demonstrativo de Resultados Histórico: O demonstrativo de resultados é um dos " +
                    "principais demonstrativos financeiros de uma empresa que mostra as receitas " +
                    "e despesas durante um período de tempo."),
    INCOME_STATEMENT_QUARTERLY("incomeStatementHistoryQuarterly", "Demonstrativo de Resultados Trimestral",
            "Demonstrativo de Resultados Trimestral: O demonstrativo de resultados é um dos " +
                    "principais demonstrativos financeiros de uma empresa que mostra as receitas " +
                    "e despesas durante um período de tempo."),
    FINANATIAL_DATA("financialData", "Dados Financeiros",
            " Dados financeiros: Este módulo fornece dados financeiros como o preço atual, " +
                    "lucro por ação, receita, crescimento de receita, etc."),
    ;
    private final String value;
    private final String name;
    private final String description;

    @Override
    public String toString() {
        return String.format("%s (%s): %s", name, value, description);
    }

    public static BrapiModuleOptions fromValue(String value) {
        for (BrapiModuleOptions range : values()) {
            if (range.value.equalsIgnoreCase(value)) {
                return range;
            }
        }
        throw new InfrastructureException(BRAPI_INTERVAL_NOT_FOUND);
    }
}
