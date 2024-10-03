package br.dev.jstec.tyginvestiment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AccountDto {
    private Long id;
    private String description;
    private String bank;
    private String accountType;
    private String currency;
    private BigDecimal initialBalance;
    private BigDecimal totalBalance;
    private BigDecimal availableBalance;
    private Long userId;
    private Long createdBy;
    private LocalDateTime createdDate;
    private Long lastModifiedBy;
    private LocalDateTime lastModifiedDate;

}
