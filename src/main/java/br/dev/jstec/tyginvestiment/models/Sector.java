package br.dev.jstec.tyginvestiment.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Sector {

    @Column(name = "sector")
    private String sector;

    @Column(name = "weight")
    private Double weight;
}

