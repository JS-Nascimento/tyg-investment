package br.dev.jstec.tyginvestiment.models;

import br.dev.jstec.tyginvestiment.enums.OperationType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "account_histories")
@EqualsAndHashCode
public class AccountHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "account_id", insertable = false, updatable = false)
    private Long accountId;

    @Column(nullable = false, length = 10)
    private String assetSymbol;

    @Enumerated(EnumType.STRING)
    private OperationType operation;

    @Column(nullable = false, length = 200)
    private String historyDescription;

    @Column(precision = 12, scale = 4)
    private BigDecimal quantity;

    @Column(precision = 12, scale = 4)
    private BigDecimal value;

    @Column(nullable = false, unique = false)
    private LocalDate createdDate;

    @PreUpdate
    @PrePersist
    void prePersist() {
        if (this.createdDate == null)
            this.createdDate = LocalDate.now();
    }

    @PostLoad
    @PostPersist
    void updateAccountId() {
        if (account != null) {
            accountId = account.getId();
        }
    }
}
