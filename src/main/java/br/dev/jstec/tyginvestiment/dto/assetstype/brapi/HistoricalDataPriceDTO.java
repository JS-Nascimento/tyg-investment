package br.dev.jstec.tyginvestiment.dto.assetstype.brapi;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class HistoricalDataPriceDTO {
    private Long date;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private BigDecimal volume;
    private BigDecimal adjustedClose;
}
