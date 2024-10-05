package br.dev.jstec.tyginvestiment.clients.dto;

import br.dev.jstec.tyginvestiment.enums.AssetType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class EtfProfileDto extends AlphaVantageInformation {

    private String symbol;

    private AssetType assetType;

    private String name;

    private String description;

    private String currency;

    @JsonProperty("net_assets")
    private Long netAssets;

    @JsonProperty("net_expense_ratio")
    private Double netExpenseRatio;

    @JsonProperty("portfolio_turnover")
    private String portfolioTurnover;

    @JsonProperty("dividend_yield")
    private Double dividendYield;

    @JsonProperty("inception_date")
    private LocalDate inceptionDate;

    @JsonProperty("leveraged")
    private String leveraged;

    @JsonProperty("asset_allocation")
    private EtfProfileAssetAllocationDto assetAllocation;

    @JsonProperty("sectors")
    private List<EtfProfileSectorDto> sectors;

    @JsonProperty("holdings")
    private List<EtfProfileHoldingDto> holdings;
}
