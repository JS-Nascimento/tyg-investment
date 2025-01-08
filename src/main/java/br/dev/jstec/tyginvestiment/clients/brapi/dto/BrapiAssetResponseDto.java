package br.dev.jstec.tyginvestiment.clients.brapi.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class BrapiAssetResponseDto {
    private List<ResultDTO> results;
    private LocalDateTime requestedAt;
    private String took;

    @Data
    public static class ResultDTO {
        private String currency;
        private String shortName;
        private String longName;
        private BigDecimal regularMarketChange;
        private BigDecimal regularMarketChangePercent;
        private LocalDateTime regularMarketTime;
        private BigDecimal regularMarketPrice;
        private BigDecimal regularMarketDayHigh;
        private String regularMarketDayRange;
        private BigDecimal regularMarketDayLow;
        private BigDecimal regularMarketVolume;
        private BigDecimal regularMarketPreviousClose;
        private BigDecimal regularMarketOpen;
        private String fiftyTwoWeekRange;
        private BigDecimal fiftyTwoWeekLow;
        private BigDecimal fiftyTwoWeekHigh;
        private String symbol;
        private String usedInterval;
        private String usedRange;
        private List<HistoricalDataPriceDTO> historicalDataPrice;
        private List<String> validRanges;
        private List<String> validIntervals;
        private BalanceSheetHistoryDTO balanceSheetHistory;
        private IncomeStatementHistoryDTO incomeStatementHistory;
        private DefaultKeyStatisticsDTO defaultKeyStatistics;
        private SummaryProfileDTO summaryProfile;
        private BigDecimal priceEarnings;
        private BigDecimal earningsPerShare;
        private String logourl;
        private DividendsDataDTO dividendsData;

        @Data
        public static class HistoricalDataPriceDTO {
            private Long date;
            private BigDecimal open;
            private BigDecimal high;
            private BigDecimal low;
            private BigDecimal close;
            private BigDecimal volume;
            private BigDecimal adjustedClose;
        }

        @Data
        public static class BalanceSheetHistoryDTO {
            private List<BalanceSheetStatementDTO> balanceSheetStatements;

            @Data
            public static class BalanceSheetStatementDTO {
                private LocalDateTime endDate;
                private BigDecimal cash;
                private BigDecimal netReceivables;
                private BigDecimal inventory;
                private BigDecimal otherCurrentAssets;
                private BigDecimal totalCurrentAssets;
                private BigDecimal longTermInvestments;
                private BigDecimal propertyPlantEquipment;
                private BigDecimal otherAssets;
                private BigDecimal totalAssets;
                private BigDecimal accountsPayable;
                private BigDecimal shortLongTermDebt;
                private BigDecimal otherCurrentLiab;
                private BigDecimal longTermDebt;
                private BigDecimal otherLiab;
                private BigDecimal totalCurrentLiabilities;
                private BigDecimal totalLiab;
                private BigDecimal commonStock;
                private BigDecimal retainedEarnings;
                private BigDecimal treasuryStock;
                private BigDecimal otherStockholderEquity;
                private BigDecimal totalStockholderEquity;
                private BigDecimal netTangibleAssets;
                private BigDecimal goodWill;
                private BigDecimal intangibleAssets;
                private BigDecimal deferredLongTermAssetCharges;
                private BigDecimal minorityInterest;
                private BigDecimal capitalSurplus;
            }
        }

        @Data
        public static class IncomeStatementHistoryDTO {
            private List<IncomeStatementDTO> incomeStatementHistory;

            @Data
            public static class IncomeStatementDTO {
                private LocalDateTime endDate;
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
        }

        @Data
        public static class DefaultKeyStatisticsDTO {
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
            private LocalDateTime lastDividendDate;
        }

        @Data
        public static class SummaryProfileDTO {
            private String address1;
            private String address2;
            private String city;
            private String state;
            private String zip;
            private String country;
            private String phone;
            private String website;
            private String industry;
            private String industryKey;
            private String industryDisp;
            private String sector;
            private String sectorKey;
            private String sectorDisp;
            private String longBusinessSummary;
            private List<Object> companyOfficers;
        }

        @Data
        public static class DividendsDataDTO {
            private List<Object> cashDividends;
            private List<Object> stockDividends;
            private List<Object> subscriptions;
        }
    }
}