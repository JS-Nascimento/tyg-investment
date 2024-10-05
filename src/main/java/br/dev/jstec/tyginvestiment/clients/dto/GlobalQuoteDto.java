package br.dev.jstec.tyginvestiment.clients.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class GlobalQuoteDto extends AlphaVantageInformation {

    @JsonProperty("01. symbol")
    private String symbol;

    @JsonProperty("02. open")
    private BigDecimal open;

    @JsonProperty("03. high")
    private BigDecimal high;

    @JsonProperty("04. low")
    private BigDecimal low;

    @JsonProperty("05. price")
    private BigDecimal price;

    @JsonProperty("06. volume")
    private Long volume;

    @JsonProperty("07. latest trading day")
    private LocalDate latestTradingDay;

    @JsonProperty("08. previous close")
    private BigDecimal previousClose;

    @JsonProperty("09. change")
    private BigDecimal change;

    @JsonProperty("10. change percent")
    private String changePercent;
}

