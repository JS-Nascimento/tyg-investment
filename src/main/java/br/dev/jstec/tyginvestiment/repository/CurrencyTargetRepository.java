package br.dev.jstec.tyginvestiment.repository;

import br.dev.jstec.tyginvestiment.models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface CurrencyTargetRepository extends JpaRepository<Currency, Long> {

    boolean existsByCode(String code);

    List<Currency> findAllByCode(String code);

}
