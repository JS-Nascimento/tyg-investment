package br.dev.jstec.tyginvestiment.dto.accountsummary;

import br.dev.jstec.tyginvestiment.enums.AssetType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class InvestmentDto {

    private AssetType asset;

    private List<PortfolioOverviewDto> overview;

    private BigDecimal totalInvested;
}
