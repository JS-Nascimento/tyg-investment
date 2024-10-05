package br.dev.jstec.tyginvestiment.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
public class Cripto extends Asset {

    @Column(name = "crypto_id")
    private String id;

    @Column(name = "image")
    private String image;

    @Column(name = "current_price")
    private Double currentPrice;

    @Column(name = "market_cap")
    private Long marketCap;

    @Column(name = "market_cap_rank")
    private Integer marketCapRank;

    @Column(name = "fully_diluted_valuation")
    private Long fullyDilutedValuation;

    @Column(name = "total_volume")
    private Long totalVolume;

    @Column(name = "high_24h")
    private Double high24h;

    @Column(name = "low_24h")
    private Double low24h;

    @Column(name = "price_change_24h")
    private Double priceChange24h;

    @Column(name = "price_change_percentage_24h")
    private Double priceChangePercentage24h;

    @Column(name = "market_cap_change_24h")
    private Double marketCapChange24h;

    @Column(name = "market_cap_change_percentage_24h")
    private Double marketCapChangePercentage24h;

    @Column(name = "circulating_supply")
    private Long circulatingSupply;

    @Column(name = "total_supply")
    private Long totalSupply;

    @Column(name = "max_supply")
    private Long maxSupply;

    @Column(name = "ath")
    private Double ath;

    @Column(name = "ath_change_percentage")
    private Double athChangePercentage;

    @Column(name = "ath_date")
    private ZonedDateTime athDate;

    @Column(name = "atl")
    private Double atl;

    @Column(name = "atl_change_percentage")
    private Double atlChangePercentage;

    @Column(name = "atl_date")
    private ZonedDateTime atlDate;

    @Column(name = "roi")
    private String roi;

    @Column(name = "last_updated")
    private ZonedDateTime lastUpdated;

    @Column(name = "price_change_percentage_14d_in_currency")
    private Double priceChangePercentage14dInCurrency;
}
