package br.dev.jstec.tyginvestiment.dto.assetstype;

import br.dev.jstec.tyginvestiment.dto.assetstype.brapi.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrapiAssetDto extends AssetDto {

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
    private BigDecimal fiftyTwoWeekLow;
    private BigDecimal fiftyTwoWeekHigh;
    private BalanceSheetHistoryDto balanceSheetHistory;
    private IncomeStatementHistoryDto incomeStatementHistory;
    private DefaultKeyStatisticsDto defaultKeyStatistics;
    private SummaryProfileDto summaryProfile;
    private BigDecimal priceEarnings;
    private BigDecimal earningsPerShare;
    private String logourl;
    private DividendsDataDto dividendsData;
    private List<HistoricalDataPriceDTO> historicalDataPrice;

}
