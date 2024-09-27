package br.dev.jstec.tyginvestiment.models;

import br.dev.jstec.tyginvestiment.enums.AssetType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "assets")
@EqualsAndHashCode(callSuper = true)
public class Asset extends Auditable<Long>{

    @Id
    private String symbol;

    @Enumerated(EnumType.STRING)
    private AssetType assetType;

    @Column(length = 100)
    private String name;

    @Column(length = 2000)
    private String description;

    private String cik;
    private String exchange;
    private String currency;
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
