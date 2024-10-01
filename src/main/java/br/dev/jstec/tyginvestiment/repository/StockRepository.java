package br.dev.jstec.tyginvestiment.repository;

import br.dev.jstec.tyginvestiment.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, String> {
}
