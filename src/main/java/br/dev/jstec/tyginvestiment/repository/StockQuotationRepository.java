package br.dev.jstec.tyginvestiment.repository;

import br.dev.jstec.tyginvestiment.models.StockQuotation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockQuotationRepository extends JpaRepository<StockQuotation, Long> {
}
