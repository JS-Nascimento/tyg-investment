package br.dev.jstec.tyginvestiment.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "account_holdings")
@EqualsAndHashCode(callSuper = true)
public class AccountHolding extends Auditable<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

    @Column(precision = 12, scale = 4 )
    private BigDecimal initialQuantity;

    @Column(precision = 12, scale = 4 )
    private BigDecimal quantity;

    @Column(precision = 12, scale = 4 )
    private BigDecimal initialPrice;

    private LocalDate purchaseDate;

    private LocalDate dueDate;
}
