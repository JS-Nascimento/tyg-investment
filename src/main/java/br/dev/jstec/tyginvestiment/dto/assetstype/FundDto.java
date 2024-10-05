package br.dev.jstec.tyginvestiment.dto.assetstype;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class FundDto extends AssetDto {
    private Long netAssets;
    private Double netExpenseRatio;
    private String portfolioTurnover;
    private Double dividendYield;
    private LocalDate inceptionDate;
    private String leveraged;
    private AssetAllocationDto assetAllocation;
    private List<SectorDto> sectors;
    private List<HoldingDto> holdings;
}
