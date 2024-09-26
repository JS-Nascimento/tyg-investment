package br.dev.jstec.tyginvestiment.services.handlers;


import br.dev.jstec.tyginvestiment.dto.ConversionRateDto;
import br.dev.jstec.tyginvestiment.models.ConversionRate;
import br.dev.jstec.tyginvestiment.models.Currency;
import br.dev.jstec.tyginvestiment.repository.ConversionRatesRepository;
import br.dev.jstec.tyginvestiment.services.adapter.CurrencyAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConversionRateHandler {

    private final ConversionRatesRepository conversionRatesRepository;
    private final CurrencyAdapter currencyAdapter;

    @Transactional
    public void saveConversionRate(Currency currency, String currencyBase) {

        var response = currencyAdapter.getLiveRates();

        var rate = response.getConversionRates().get(currency.getCode());

        if (rate == null) {
            throw new IllegalArgumentException("Invalid conversion rate");
        }

        var entity = new ConversionRate();
        entity.setSourceCurrency(currencyBase);
        entity.setTargetCurrency(currency);
        entity.setRate(rate);
        entity.setRateDate(LocalDateTime.now());

        conversionRatesRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public ConversionRateDto findLastRateToConversion(String sourceCurrency, Long targetCurrencyId) {
        return conversionRatesRepository.findLastConversionRate(sourceCurrency, targetCurrencyId)
                .map(cr -> new ConversionRateDto(cr.getRate(), cr.getRateDate()))
                .orElse(null);
    }

    @Transactional
    public void updateConversionRate(Set<Currency> targets, String currencyBase) {

        var response = currencyAdapter.getLiveRates();

        targets.forEach(target -> {
            var rate = response.getConversionRates().get(target.getCode());
            if (rate == null) {
                rate = 0.0;
                log.error("Invalid conversion rate to {}", target.getCode());
            }
            var entity = new ConversionRate();
            entity.setSourceCurrency(currencyBase);
            entity.setTargetCurrency(target);
            entity.setRate(rate);
            entity.setRateDate(LocalDateTime.now());
            conversionRatesRepository.save(entity);
        });
    }
}
