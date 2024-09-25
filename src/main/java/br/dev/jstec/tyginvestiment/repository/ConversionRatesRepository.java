package br.dev.jstec.tyginvestiment.repository;

import br.dev.jstec.tyginvestiment.models.ConversionRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ConversionRatesRepository extends JpaRepository<ConversionRate, Long> {

    @Query("""
            SELECT cr
            FROM ConversionRate cr 
            WHERE cr.sourceCurrency = :sourceCurrency 
                AND cr.targetCurrency.id = :targetCurrencyId 
            ORDER BY cr.rateDate DESC
            """)
    Optional<ConversionRate> findTopBySourceCurrencyAndTargetCurrencyOrderByRateDateDesc(@Param("sourceCurrency") String sourceCurrency, @Param("targetCurrencyId") Long targetCurrencyId);
}
