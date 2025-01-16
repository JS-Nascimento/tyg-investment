package br.dev.jstec.tyginvestiment.models;

import br.dev.jstec.tyginvestiment.models.brapiassets.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
public class BrapiAsset extends Asset {

    private String shortName;

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

    @Column(name = "fiftyTwoWeekLow", precision = 38, scale = 2, nullable = false)
    private BigDecimal fiftyTwoWeekLow = BigDecimal.ZERO;

    @Column(name = "fiftyTwoWeekHigh", precision = 38, scale = 2, nullable = false)
    private BigDecimal fiftyTwoWeekHigh = BigDecimal.ZERO;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "balance_sheet_history_id", referencedColumnName = "id")
    private BalanceSheetHistory balanceSheetHistory;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "income_statement_history_id", referencedColumnName = "id")
    private IncomeStatementHistory incomeStatementHistory;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "default_key_statistics_id", referencedColumnName = "id")
    private DefaultKeyStatistics defaultKeyStatistics;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "summary_profile_id", referencedColumnName = "id")
    private SummaryProfile summaryProfile;

    private BigDecimal priceEarnings;

    private BigDecimal earningsPerShare;

    private String logourl;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dividends_data_id", referencedColumnName = "id")
    private DividendsData dividendsData;
}
