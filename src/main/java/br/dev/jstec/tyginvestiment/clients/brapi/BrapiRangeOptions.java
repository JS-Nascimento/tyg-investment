package br.dev.jstec.tyginvestiment.clients.brapi;

import br.dev.jstec.tyginvestiment.exception.InfrastructureException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static br.dev.jstec.tyginvestiment.exception.ErrorMessage.BRAPI_RANGE_NOT_FOUND;

@AllArgsConstructor
@Getter
public enum BrapiRangeOptions {

    ONE_DAY("1d", "Um dia", "Um dia de negociação, incluindo o dia atual"),
    FIVE_DAYS("5d", "Cinco dias", "Cinco dias de negociação, incluindo o dia atual"),
    ONE_MONTH("1mo", "Um mês", "Um mês de negociação, incluindo o dia atual"),
    THREE_MONTHS("3mo", "Três meses", "Três meses de negociação, incluindo o dia atual"),
    SIX_MONTHS("6mo", "Seis meses", "Seis meses de negociação, incluindo o dia atual"),
    ONE_YEAR("1y", "Um ano", "Um ano de negociação, incluindo o dia atual"),
    TWO_YEARS("2y", "Dois anos", "Dois anos de negociação, incluindo o dia atual"),
    FIVE_YEARS("5y", "Cinco anos", "Cinco anos de negociação, incluindo o dia atual"),
    TEN_YEARS("10y", "Dez anos", "Dez anos de negociação, incluindo o dia atual"),
    YEAR_TO_DATE("ytd", "Ano até hoje", "O ano atual até a data atual"),
    MAX("max", "Todos os dados", "Todos os dados disponíveis");

    private final String value;
    private final String name;
    private final String description;

    @Override
    public String toString() {
        return String.format("%s (%s): %s", name, value, description);
    }

    public static BrapiRangeOptions fromValue(String value) {
        for (BrapiRangeOptions range : values()) {
            if (range.value.equalsIgnoreCase(value)) {
                return range;
            }
        }
        throw new InfrastructureException(BRAPI_RANGE_NOT_FOUND);
    }
}

