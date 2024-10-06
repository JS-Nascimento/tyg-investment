package br.dev.jstec.tyginvestiment.models;

import br.dev.jstec.tyginvestiment.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "asset_transactions",
        indexes = {
                @Index(name = "idx_asset_transactions_transaction_date", columnList = "transaction_date"),
                @Index(name = "idx_asset_transactions_transaction_type", columnList = "transaction_type"),
                @Index(name = "idx_asset_transactions_asset_id", columnList = "asset_id"),
                @Index(name = "idx_asset_transactions_account_id", columnList = "account_id")
        })
public class AssetTransaction extends Auditable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(nullable = false)
    private LocalDate transactionDate;

    private Double quantity;

    private BigDecimal value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @PrePersist
    @PreUpdate
    private void setTransactionDate() {
        if (this.transactionDate == null) {
            this.transactionDate = LocalDate.now();
        }
    }
}
