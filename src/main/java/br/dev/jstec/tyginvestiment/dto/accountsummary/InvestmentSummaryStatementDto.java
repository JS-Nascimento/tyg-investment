package br.dev.jstec.tyginvestiment.dto.accountsummary;

import br.dev.jstec.tyginvestiment.dto.assetstype.AssetDto;
import br.dev.jstec.tyginvestiment.enums.AssetType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvestmentSummaryStatementDto {
    private Long id;
    private String description;
    private String bank;
    private String accountType;
    private String currency;
    private BigDecimal initialBalance;
    private BigDecimal totalBalance;
    private BigDecimal availableBalance;

    private List<InvestmentDto> investments;
}
