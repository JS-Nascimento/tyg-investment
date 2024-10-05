package br.dev.jstec.tyginvestiment.services.handlers;

import br.dev.jstec.tyginvestiment.dto.BaseCurrencyDto;
import br.dev.jstec.tyginvestiment.dto.CurrencyDto;
import br.dev.jstec.tyginvestiment.repository.CurrencyTargetRepository;
import br.dev.jstec.tyginvestiment.services.mappers.CurrencyMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Currency;
import java.util.HashSet;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;


@Component
@RequiredArgsConstructor
public class CurrencyHandler {

    private final CurrencyTargetRepository currencyRepository;
    private final ConversionRateHandler conversionRateHandler;
    private final CurrencyMapper mapper;

    @Getter
    @Value("${app.config.currency-base}")
    private String currencyBase;

    @Getter
    @Value("${app.config.decimal-places}")
    private Integer decimalPlaces;

    @Transactional
    public BaseCurrencyDto saveCurrency(CurrencyDto dto) {

        validateCurrency(dto);
        var currency = Currency.getInstance(dto.getCode());

        var currencyExist = currencyRepository.findByCode(currency.getCurrencyCode());
        if (currencyExist.isPresent()) {
            return getBaseCurrency();
        }

        var entity = mapper.toEntity(dto);
        var saved = currencyRepository.save(entity);

        conversionRateHandler.saveConversionRate(saved, currencyBase);

        return getBaseCurrency();
    }

    @Transactional(readOnly = true)
    public BaseCurrencyDto getBaseCurrency() {

         var currency = currencyRepository.findAll();

        var baseCurrency = Currency.getInstance(currencyBase);

        var dto = new BaseCurrencyDto();
        dto.setCode(baseCurrency.getCurrencyCode());
        dto.setName(baseCurrency.getDisplayName());
        dto.setSymbol(baseCurrency.getSymbol());
        dto.setCurrencies(currency.stream()
                .map(c -> {
                    var currencyDto = new CurrencyDto();
                    currencyDto.setCode(c.getCode());
                    currencyDto.setName(c.getName());
                    currencyDto.setSymbol(c.getSymbol());
                    currencyDto.setDecimalPlaces(c.getDecimalPlaces());
                    currencyDto.setConversionRate(
                            conversionRateHandler.findLastRateToConversion(currencyBase, c.getId()));
                    return currencyDto;
                })
                .toList());

        return dto;
    }

    @Transactional
    public void updateCurrency() {
        var currencies = new HashSet<>(currencyRepository.findAll()) ;

        conversionRateHandler.updateConversionRate(currencies, currencyBase);
    }

    @Transactional(readOnly = true)
    public br.dev.jstec.tyginvestiment.models.Currency getCurrencyByCode(String code) {
        return currencyRepository.findByCode(code).orElse(null);
    }

    @Transactional(readOnly = true)
    public boolean exists(String code) {
        return currencyRepository.existsByCode(code);
    }

    private void validateCurrency(CurrencyDto dto) {

        if (isNull(dto) || isBlank(dto.getCode())) {
            throw new IllegalArgumentException("Invalid currency data");
        }
    }
}
