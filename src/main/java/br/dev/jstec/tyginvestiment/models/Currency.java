package br.dev.jstec.tyginvestiment.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "currencies")
@Data
public class Currency extends Auditable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 3, nullable = false, unique = true)
    private String code;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 3, nullable = false)
    private String symbol;

    @Column(nullable = false, columnDefinition = "int default 2")
    private int decimalPlaces;

    private boolean currencyBase;

}
