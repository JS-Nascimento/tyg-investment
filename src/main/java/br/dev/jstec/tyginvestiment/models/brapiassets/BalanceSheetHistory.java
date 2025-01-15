package br.dev.jstec.tyginvestiment.models.brapiassets;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "balance_sheet_history")
public class BalanceSheetHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id")
    private List<BalanceSheetStatement> balanceSheetStatements;

}

