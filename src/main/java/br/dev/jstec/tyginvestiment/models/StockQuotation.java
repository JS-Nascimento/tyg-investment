package br.dev.jstec.tyginvestiment.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.math.BigDecimal;
import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.CascadeType.ALL;

@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode()
@Table(indexes = {
        @Index(name = "idx_data", columnList = "date"),
})
public class StockQuotation {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    @Cascade(ALL)
    private Asset stock;

    private LocalDate date;

    private BigDecimal open;

    private BigDecimal high;

    private BigDecimal low;

    private BigDecimal close;

    private Long volume;

    private BigDecimal previousClose;

    private BigDecimal change;

    private String changePercent;
}
