package br.dev.jstec.tyginvestiment.models.brapiassets;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "dividends_data")
public class DividendsData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "cash_dividends", joinColumns = @JoinColumn(name = "dividends_data_id"))
    private List<CashDividend> cashDividends;

    @ElementCollection
    @CollectionTable(name = "stock_dividends", joinColumns = @JoinColumn(name = "dividends_data_id"))
    private List<String> stockDividends;

    @ElementCollection
    @CollectionTable(name = "subscriptions", joinColumns = @JoinColumn(name = "dividends_data_id"))
    private List<String> subscriptions;
}
