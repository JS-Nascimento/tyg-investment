package br.dev.jstec.tyginvestiment.models;

import br.dev.jstec.tyginvestiment.enums.AssetType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "assets")
@EqualsAndHashCode(callSuper = true)
public class Asset extends Auditable<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Enumerated(EnumType.STRING)
    private AssetType type;

    @Column(length = 10, nullable = false)
    private String symbol;

    @Column(length = 50)
    private String name;

    private double initialQuantity;

    private double quantity;

    private BigDecimal initialPrice;

    private BigDecimal price;

    private BigDecimal averagePrice;

    private LocalDate purchaseDate;
}
