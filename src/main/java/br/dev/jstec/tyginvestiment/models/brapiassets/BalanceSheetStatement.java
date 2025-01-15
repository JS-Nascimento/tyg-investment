package br.dev.jstec.tyginvestiment.models.brapiassets;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "balance_sheet_statement")
public class BalanceSheetStatement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private OffsetDateTime endDate;
    private BigDecimal cash;
    private BigDecimal netReceivables;
    private BigDecimal inventory;
    private BigDecimal otherCurrentAssets;
    private BigDecimal totalCurrentAssets;
    private BigDecimal longTermInvestments;
    private BigDecimal propertyPlantEquipment;
    private BigDecimal otherAssets;
    private BigDecimal totalAssets;
    private BigDecimal accountsPayable;
    private BigDecimal shortLongTermDebt;
    private BigDecimal otherCurrentLiab;
    private BigDecimal longTermDebt;
    private BigDecimal otherLiab;
    private BigDecimal totalCurrentLiabilities;
    private BigDecimal totalLiab;
    private BigDecimal commonStock;
    private BigDecimal retainedEarnings;
    private BigDecimal treasuryStock;
    private BigDecimal otherStockholderEquity;
    private BigDecimal totalStockholderEquity;
    private BigDecimal netTangibleAssets;
    private BigDecimal goodWill;
    private BigDecimal intangibleAssets;
    private BigDecimal deferredLongTermAssetCharges;
    private BigDecimal minorityInterest;
    private BigDecimal capitalSurplus;
}
