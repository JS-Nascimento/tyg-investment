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
@Table(name = "assets")
@EqualsAndHashCode(callSuper = true)
public class AssetAllocation extends Auditable<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

    private double initialQuantity;

    private double quantity;

    private BigDecimal initialPrice;

    private BigDecimal price;

    private BigDecimal averagePrice;

    private LocalDate purchaseDate;
}
