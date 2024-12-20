package br.dev.jstec.tyginvestiment.services.handlers;

import br.dev.jstec.tyginvestiment.clients.CurrencyClient;
import br.dev.jstec.tyginvestiment.dto.BaseCurrencyDto;
import br.dev.jstec.tyginvestiment.dto.CurrencyDto;
import br.dev.jstec.tyginvestiment.dto.currencies.CurrencyDataDto;
import br.dev.jstec.tyginvestiment.dto.currencies.CurrencyQuotationHistoryDto;
import br.dev.jstec.tyginvestiment.exception.BusinessException;
import br.dev.jstec.tyginvestiment.repository.CurrencyTargetRepository;
import br.dev.jstec.tyginvestiment.services.mappers.CurrencyMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static br.dev.jstec.tyginvestiment.config.security.TenantContext.getTenantBaseCurrency;
import static br.dev.jstec.tyginvestiment.exception.BusinessErrorMessage.CURRENCY_NOT_FOUND;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;


@Component
@RequiredArgsConstructor
@Slf4j
public class CurrencyHandler {

    private final CurrencyTargetRepository currencyRepository;
    private final ConversionRateHandler conversionRateHandler;
    private final CurrencyMapper mapper;
    private final CurrencyClient client;

    @Getter
    @Value("${app.config.decimal-places}")
    private Integer decimalPlaces;

    @Getter
    @Value("${app.config.currency-base}")
    private String currencyBaseDefault;

    @Transactional
    public BaseCurrencyDto saveCurrency(CurrencyDto dto) {

        validateCurrency(dto);
        var currency = Currency.getInstance(dto.getCode());

        var currencyExist = currencyRepository.findByCode(currency.getCurrencyCode());
        if (currencyExist.isPresent()) {
            return getBaseCurrency();
        }

        if (isNull(dto.getDecimalPlaces())) {
            dto.setDecimalPlaces(decimalPlaces);
        }

        dto.setCurrencyBase(false);
        dto.setCode(currency.getCurrencyCode());
        dto.setName(currency.getDisplayName(Locale.getDefault()));
        dto.setSymbol(currency.getSymbol(Locale.getDefault()));

        var entity = mapper.toEntity(dto);
        var saved = currencyRepository.save(entity);

        conversionRateHandler.saveConversionRate(saved, getTenantBaseCurrency());

        return getBaseCurrency();
    }

    @Transactional(readOnly = true)
    public BaseCurrencyDto getBaseCurrency() {

        var currency = currencyRepository.findAll();

        var baseCurrency = Currency.getInstance(getTenantBaseCurrency());

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
                            conversionRateHandler.findLastRateToConversion(getTenantBaseCurrency(), c.getId()));
                    return currencyDto;
                })
                .toList());

        return dto;
    }

    @Transactional
    public void updateCurrency() {
        var currencies = new HashSet<>(currencyRepository.findAll());

        conversionRateHandler.updateConversionRate(currencies, currencyBaseDefault);
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
            throw new BusinessException(CURRENCY_NOT_FOUND);
        }
    }

    @Transactional
    public void verifyAndSaveIfNotExists(String code) {
        if (!exists(code)) {
            var currency = Currency.getInstance(code);
            var currencyDto = new CurrencyDto();
            currencyDto.setCode(currency.getCurrencyCode());
            currencyDto.setName(currency.getDisplayName());
            currencyDto.setSymbol(currency.getSymbol());
            currencyDto.setDecimalPlaces(decimalPlaces);
            currencyDto.setCurrencyBase(false);

            saveCurrency(currencyDto);
        }
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<CurrencyQuotationHistoryDto> getHistoryByCodeWithLimit(String code, int limit) {
        if (isBlank(code)) {
            throw new BusinessException(CURRENCY_NOT_FOUND);
        }
        if (!exists(code)) {
            throw new BusinessException(CURRENCY_NOT_FOUND);
        }
        if (limit <= 0) {
            limit = 30;
        }
        return currencyRepository.getHistory(code, limit)
                .stream()
                .map(tuple -> new CurrencyQuotationHistoryDto(
                        tuple.get(0, Double.class),
                        tuple.get(1, Double.class),
                        tuple.get(2, Double.class),
                        tuple.get(3, Date.class)
                ))
                .toList();
    }

    @Cacheable(value = "exchange-list")
    public List<CurrencyDataDto> getExchangeList() {
        var response = client.getExchangeList();

        if (!(response instanceof Map<?, ?> responseMap)) {
            return Collections.emptyList();
        }


        List<List<String>> supportedCodes = (List<List<String>>) responseMap.get("supported_codes");

        return Optional.ofNullable(supportedCodes)
                .orElse(Collections.emptyList())
                .stream()
                .map(this::createCurrencyData)
                .filter(Objects::nonNull)
                .toList(); // Converte para lista
    }

    private CurrencyDataDto createCurrencyData(List<String> currencyPair) {
        if (currencyPair.size() < 2) {
            return null;
        }

        try {
            String code = currencyPair.get(0);
            Currency systemCurrency = Currency.getInstance(code);
            String description = systemCurrency.getDisplayName(Locale.getDefault());

            log.info("Currency code: {}, description: {}", code, description);

            return new CurrencyDataDto(code, description);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("The input currency code is not a valid ISO 4217 code")) {
                log.warn("Código de moeda inválido: {}", currencyPair.get(0));
            }
            return null;
        }
    }
}
