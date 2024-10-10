package br.dev.jstec.tyginvestiment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AccountHistoryDto {

    private Long id;

    private Long accountId;

    private String assetSymbol;

    private String operation;

    private String historyDescription;

    private BigDecimal quantity;

    private BigDecimal value;

    private LocalDate createdDate;
}
