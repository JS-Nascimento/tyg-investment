package br.dev.jstec.tyginvestiment.dto;

import br.dev.jstec.tyginvestiment.dto.assetstype.AssetDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AccountHoldingDto {

    private Long id;

    private AccountDto account;

    private AssetDto asset;

    private BigDecimal initialQuantity;

    private BigDecimal quantity;

    private BigDecimal initialPrice;

    private LocalDate purchaseDate;

    private LocalDate dueDate;
}
