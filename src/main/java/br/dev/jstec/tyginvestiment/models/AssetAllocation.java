package br.dev.jstec.tyginvestiment.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class AssetAllocation {

    @Column(name = "domestic_equities")
    private Double domesticEquities;

    @Column(name = "foreign_equities")
    private Double foreignEquities;

    @Column(name = "bond")
    private Double bond;

    @Column(name = "cash")
    private Double cash;

    @Column(name = "other")
    private Double other;
}
