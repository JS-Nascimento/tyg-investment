package br.dev.jstec.tyginvestiment.dto.accountsummary;

import br.dev.jstec.tyginvestiment.enums.AssetType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PortfolioOverviewDto {

    private Long id;

    private AssetType asset;

    private LocalDate purchaseDate;

    private LocalDate dueDate;

    private BigDecimal initialQuantity;

    private BigDecimal quantity;

    private BigDecimal initialPrice;

    private BigDecimal currentPrice;

    private BigDecimal rate;

    private BigDecimal totalInvested;
}
