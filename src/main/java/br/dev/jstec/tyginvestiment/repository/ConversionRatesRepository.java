package br.dev.jstec.tyginvestiment.repository;

import br.dev.jstec.tyginvestiment.models.ConversionRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
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
    @Query("""
            SELECT cr
            FROM ConversionRate cr
            WHERE cr.sourceCurrency = :sourceCurrency
                AND cr.targetCurrency.id = :targetCurrencyId
                AND cr.rateDate = (
                                    SELECT MAX(cr2.rateDate)
                                    FROM ConversionRate cr2
                                    WHERE cr2.sourceCurrency = :sourceCurrency
                                    AND cr2.targetCurrency.id = :targetCurrencyId
                                     )
            """)
    Optional<ConversionRate> findLastConversionRate(@Param("sourceCurrency") String sourceCurrency, @Param("targetCurrencyId") Long targetCurrencyId);
}
