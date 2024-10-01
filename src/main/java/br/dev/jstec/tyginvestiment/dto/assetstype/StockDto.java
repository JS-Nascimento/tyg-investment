package br.dev.jstec.tyginvestiment.dto.assetstype;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class StockDto extends AssetDto {
    private String cik;
    private String exchange;
    private String country;
    private String sector;
    private String industry;
    private String officialSite;
    private String marketCapitalization;
    private String ebitda;
    private String peRatio;
    private String pegRatio;
    private String bookValue;
    private String dividendPerShare;
    private String dividendYield;
    private String eps;
    private String revenuePerShareTTM;
    private String profitMargin;
    private String operatingMarginTTM;
    private String returnOnAssetsTTM;
    private String returnOnEquityTTM;
    private String revenueTTM;
    private String grossProfitTTM;
    private String analystTargetPrice;
    private String priceToSalesRatioTTM;
    private String priceToBookRatio;
    private String fiftyTwoWeekHigh;
    private String fiftyTwoWeekLow;
    private String fiftyDayMovingAverage;
    private String twoHundredDayMovingAverage;
    private String sharesOutstanding;
    private LocalDate dividendDate;
    private LocalDate exDividendDate;

}
