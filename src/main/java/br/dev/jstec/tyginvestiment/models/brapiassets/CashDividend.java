package br.dev.jstec.tyginvestiment.models.brapiassets;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Embeddable
public class CashDividend {

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
