package br.dev.jstec.tyginvestiment.clients.brapi;

import br.dev.jstec.tyginvestiment.exception.InfrastructureException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static br.dev.jstec.tyginvestiment.exception.ErrorMessage.BRAPI_INTERVAL_NOT_FOUND;

@AllArgsConstructor
@Getter
public enum BrapiIntervalOptions {

    ONE_MINUTE("1m", "Um minuto", "Um minuto"),
    TWO_MINUTES("2m", "Dois minutos", "Dois minutos"),
    FIVE_MINUTES("5m", "Cinco minutos", "Cinco minutos"),
    FIFTEEN_MINUTES("15m", "Quinze minutos", "Quinze minutos"),
    THIRTY_MINUTES("30m", "Trinta minutos", "Trinta minutos"),
    SIXTY_MINUTES("60m", "Sessenta minutos", "Sessenta minutos"),
    NINETY_MINUTES("90m", "Noventa minutos", "Noventa minutos"),
    ONE_HOUR("1h", "Uma hora", "Uma hora"),
    ONE_DAY("1d", "Um dia", "Um dia"),
    FIVE_DAYS("5d", "Cinco dias", "Cinco dias"),
    ONE_WEEK("1wk", "Uma semana", "Uma semana"),
    ONE_MONTH("1mo", "Um mês", "Um mês"),
    THREE_MONTHS("3mo", "Três meses", "Três meses");

    private final String value;
    private final String name;
    private final String description;

    @Override
    public String toString() {
        return String.format("%s (%s): %s", name, value, description);
    }

    public static BrapiIntervalOptions fromValue(String value) {
        for (BrapiIntervalOptions range : values()) {
            if (range.value.equalsIgnoreCase(value)) {
                return range;
            }
        }
        throw new InfrastructureException(BRAPI_INTERVAL_NOT_FOUND);
    }
}

