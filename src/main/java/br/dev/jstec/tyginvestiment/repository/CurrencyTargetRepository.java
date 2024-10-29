package br.dev.jstec.tyginvestiment.repository;

import br.dev.jstec.tyginvestiment.models.Currency;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CurrencyTargetRepository extends JpaRepository<Currency, Long> {

    boolean existsByCode(String code);

    Set<Currency> findAllByCode(String codes);

    Optional<Currency> findByCode(String code);

    @Query(value = """
            SELECT AVG(1/avg_rate) AS average_rate,
                   MAX(1/max_rate) AS max_rate,
                   MIN(1/min_rate) AS min_rate,
                   ratedate::date AS date_only
            FROM (
                     SELECT AVG(conversion_rates.rate) AS avg_rate,
                            MAX(conversion_rates.rate) AS max_rate,
                            MIN(conversion_rates.rate) AS min_rate,
                            ratedate
                     FROM currencies LEFT JOIN conversion_rates
                         on currencies.id = conversion_rates.target_currency_id
                     WHERE conversion_rates.rate IS NOT NULL
                       AND currencies.code = :code
                     GROUP BY ratedate
                 ) AS daily_rates
            GROUP BY date_only
            ORDER BY date_only DESC
            LIMIT :limit ;
            """, nativeQuery = true)
    List<Tuple> getHistory(String code, int limit);

}
