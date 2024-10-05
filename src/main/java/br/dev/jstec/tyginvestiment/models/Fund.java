package br.dev.jstec.tyginvestiment.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
public class Fund extends Asset {

    @Column(name = "net_assets")
    private Long netAssets;

    @Column(name = "net_expense_ratio")
    private Double netExpenseRatio;

    @Column(name = "portfolio_turnover")
    private String portfolioTurnover;

    @Column(name = "dividend_yield")
    private Double dividendYield;

    @Column(name = "inception_date")
    private LocalDate inceptionDate;

    @Column(name = "leveraged")
    private String leveraged;

    @Embedded
    private AssetAllocation assetAllocation;

    @ElementCollection
    @CollectionTable(name = "sectors", joinColumns = @JoinColumn(name = "fund_info_id"))
    private List<Sector> sectors;

    @ElementCollection
    @CollectionTable(name = "holdings", joinColumns = @JoinColumn(name = "fund_info_id"))
    private List<Holding> holdings;
}
