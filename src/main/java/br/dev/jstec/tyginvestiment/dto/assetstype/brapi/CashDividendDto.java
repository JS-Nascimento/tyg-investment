package br.dev.jstec.tyginvestiment.dto.assetstype.brapi;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CashDividendDto {

    private String assetIssued;
    private LocalDateTime paymentDate;
    private BigDecimal rate;
    private String relatedTo;
    private LocalDateTime approvedOn;
    private String isinCode;
    private String label;
    private LocalDateTime lastDatePrior;
    private String remarks;

}
