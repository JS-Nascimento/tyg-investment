package br.dev.jstec.tyginvestiment.dto.assetstype;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CriptoDto extends AssetDto {

    private String id;
    private String image;
    private Double currentPrice;
    private Long marketCap;
    private Integer marketCapRank;
    private Long fullyDilutedValuation;
    private Long totalVolume;
    private Double high24h;
    private Double low24h;
    private Double priceChange24h;
    private Double priceChangePercentage24h;
    private Double marketCapChange24h;
    private Double marketCapChangePercentage24h;
    private Long circulatingSupply;
    private Long totalSupply;
    private Long maxSupply;
    private Double ath;
    private Double athChangePercentage;
    private ZonedDateTime athDate;
    private Double atl;
    private Double atlChangePercentage;
    private ZonedDateTime atlDate;
    private String roi;
    private ZonedDateTime lastUpdated;
    private Double priceChangePercentage14dInCurrency;
}
