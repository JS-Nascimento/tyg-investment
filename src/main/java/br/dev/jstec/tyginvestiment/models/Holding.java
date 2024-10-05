package br.dev.jstec.tyginvestiment.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Holding {

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "description")
    private String description;

    @Column(name = "weight")
    private Double weight;
}

