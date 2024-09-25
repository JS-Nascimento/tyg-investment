package br.dev.jstec.tyginvestiment.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "conversion_rates")
public class ConversionRate extends Auditable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sourceCurrency;

    @ManyToOne
    @JoinColumn(name = "target_currency_id")
    private Currency targetCurrency;

    private double rate;

    private LocalDateTime rateDate;
}
