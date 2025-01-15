package br.dev.jstec.tyginvestiment.dto.assetstype.brapi;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class IncomeStatementDto {

    private Long id;
    private OffsetDateTime endDate;
    private BigDecimal totalRevenue;
    private BigDecimal costOfRevenue;
    private BigDecimal grossProfit;
    private BigDecimal sellingGeneralAdministrative;
    private BigDecimal otherOperatingExpenses;
    private BigDecimal totalOperatingExpenses;
    private BigDecimal operatingIncome;
    private BigDecimal totalOtherIncomeExpenseNet;
    private BigDecimal ebit;
    private BigDecimal interestExpense;
    private BigDecimal incomeBeforeTax;
    private BigDecimal incomeTaxExpense;
    private BigDecimal minorityInterest;
    private BigDecimal netIncomeFromContinuingOps;
    private BigDecimal discontinuedOperations;
    private BigDecimal netIncome;
    private BigDecimal netIncomeApplicableToCommonShares;
}
