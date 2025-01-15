package br.dev.jstec.tyginvestiment.dto.assetstype.brapi;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data

public class DefaultKeyStatisticsDto {

    private Long id;

    private int priceHint;
    private BigDecimal enterpriseValue;
    private BigDecimal forwardPE;
    private BigDecimal profitMargins;
    private BigDecimal floatShares;
    private BigDecimal sharesOutstanding;
    private BigDecimal heldPercentInsiders;
    private BigDecimal heldPercentInstitutions;
    private BigDecimal impliedSharesOutstanding;
    private BigDecimal bookValue;
    private BigDecimal priceToBook;
    private LocalDateTime lastFiscalYearEnd;
    private LocalDateTime nextFiscalYearEnd;
    private LocalDateTime mostRecentQuarter;
    private BigDecimal earningsQuarterlyGrowth;
    private BigDecimal netIncomeToCommon;
    private BigDecimal trailingEps;
    private BigDecimal forwardEps;
    private String lastSplitFactor;
    private Long lastSplitDate;
    private BigDecimal enterpriseToRevenue;
    private BigDecimal enterpriseToEbitda;
    private BigDecimal weekChange52;
    private BigDecimal sandP52WeekChange;
    private BigDecimal lastDividendValue;
    private OffsetDateTime lastDividendDate;
}
