package br.dev.jstec.tyginvestiment.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cascade;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.CascadeType.ALL;

@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode()
@Table(indexes = {
        @Index(name = "idx_data", columnList = "date"),
})
@BatchSize(size = 50)
public class StockQuotation {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    @Cascade(ALL)
    private Asset asset;

    private LocalDateTime date;

    private BigDecimal open;

    private BigDecimal high;

    private BigDecimal low;

    private BigDecimal close;

    private Double volume;

    private BigDecimal previousClose;

    private BigDecimal change;

    private String changePercent;

    private Double marketCaps;
}
