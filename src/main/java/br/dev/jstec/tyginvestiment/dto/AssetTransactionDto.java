package br.dev.jstec.tyginvestiment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AssetTransactionDto {

    private Long id;
    private String description;
    private String transactionType;
    private LocalDate transactionDate;
    private BigDecimal quantity;
    private BigDecimal value;
    private String assetId;
    private Long accountId;
    private Long createdBy;
    private LocalDateTime createdAt;
}
