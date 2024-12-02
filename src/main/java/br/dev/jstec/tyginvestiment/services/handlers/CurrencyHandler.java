package br.dev.jstec.tyginvestiment.services.handlers;

import br.dev.jstec.tyginvestiment.clients.CurrencyClient;
import br.dev.jstec.tyginvestiment.dto.CurrencyDto;
import br.dev.jstec.tyginvestiment.dto.currencies.CurrencyDataDto;
import br.dev.jstec.tyginvestiment.dto.currencies.CurrencyQuotationHistoryDto;
import br.dev.jstec.tyginvestiment.exception.BusinessException;
import br.dev.jstec.tyginvestiment.models.UserCurrencies;
import br.dev.jstec.tyginvestiment.repository.CurrencyTargetRepository;
import br.dev.jstec.tyginvestiment.repository.UserCurrenciesRepository;
import br.dev.jstec.tyginvestiment.repository.UserRepository;
import br.dev.jstec.tyginvestiment.services.mappers.CurrencyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static br.dev.jstec.tyginvestiment.config.security.TenantContext.getTenant;
import static br.dev.jstec.tyginvestiment.config.security.TenantContext.getTenantSettings;
import static br.dev.jstec.tyginvestiment.exception.BusinessErrorMessage.CURRENCY_NOT_FOUND;
import static br.dev.jstec.tyginvestiment.exception.BusinessErrorMessage.USER_NOT_FOUND;
import static java.util.Locale.forLanguageTag;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isBlank;


@Component
@RequiredArgsConstructor
@Slf4j
public class CurrencyHandler {

    private final CurrencyTargetRepository currencyRepository;
    private final ConversionRateHandler conversionRateHandler;
    private final CurrencyMapper mapper;
    private final CurrencyClient client;
    private final UserRepository userRepository;
    private final UserCurrenciesRepository userCurrenciesRepository;

    @Transactional
    public CurrencyDto saveCurrency(CurrencyDto dto) {

        validateCurrency(dto);
        var currency = Currency.getInstance(dto.getCode());

        var currencyExist = currencyRepository.findByCode(currency.getCurrencyCode())
                .stream()
                .findFirst()
                .orElse(saveCurrency(dto, false));

        var user = userRepository.findByTenantId(getTenant())
                .stream()
                .findFirst()
                .orElseThrow(() -> new BusinessException(USER_NOT_FOUND));


        var currencyFound = user.getUserCurrencies().stream()
                .filter(userCurrency -> userCurrency.getCurrency().getCode().equals(currencyExist.getCode()))
                .findFirst()
                .map(UserCurrencies::getCurrency)
                .map(mapper::toDto)
                .orElse(null);

        if (nonNull(currencyFound)) {
            return currencyFound;
        }

        var userCurrency = new UserCurrencies();
        userCurrency.setUser(user);
        userCurrency.setCurrency(currencyExist);
        userCurrency.setActive(true);

        var currencySaved = userCurrenciesRepository.save(userCurrency);
        return mapper.toDto(currencySaved.getCurrency());
    }

    private br.dev.jstec.tyginvestiment.models.Currency saveCurrency(CurrencyDto dto, boolean isBase) {

        var currency = Currency.getInstance(dto.getCode());

        if (isNull(dto.getDecimalPlaces())) {
            dto.setDecimalPlaces(getTenantSettings().getDecimalPlaces());
        }

        var locale = isBlank(getTenantSettings().getLocale()) ? Locale.getDefault() : forLanguageTag(getTenantSettings().getLocale());

        dto.setCurrencyBase(isBase);
        dto.setCode(currency.getCurrencyCode());
        dto.setName(currency.getDisplayName(locale));
        dto.setSymbol(currency.getSymbol(locale));

        var entity = mapper.toEntity(dto);
        var saved = currencyRepository.save(entity);

        conversionRateHandler
                .saveConversionRate(saved, getTenantSettings().getBaseCurrency());

        return saved;
    }

    @Transactional(readOnly = true)
    public CurrencyDto getCurrencyDto(String code) {

        if (isBlank(code)) {
            throw new BusinessException(CURRENCY_NOT_FOUND);
        }

        return currencyRepository.findByCode(code)
                .map(mapper::toDto)
                .orElseThrow(() -> new BusinessException(CURRENCY_NOT_FOUND));
    }

    @Transactional
    public void updateCurrency() {
        var currencies = new HashSet<>(currencyRepository.findAll());

        conversionRateHandler.updateConversionRate(currencies, getTenantSettings().getBaseCurrency());
    }

    @Transactional(readOnly = true)
    public br.dev.jstec.tyginvestiment.models.Currency getCurrencyByCode(String code) {
        return currencyRepository.findByCode(code).orElse(null);
    }

    @Transactional(readOnly = true)
    public Boolean exists(String code) {
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
            currencyDto.setDecimalPlaces(getTenantSettings().getDecimalPlaces());
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
                .toList();
    }

    private CurrencyDataDto createCurrencyData(List<String> currencyPair) {
        if (currencyPair.size() < 2) {
            return null;
        }

        try {
            String code = currencyPair.getFirst();
            Currency systemCurrency = Currency.getInstance(code);
            String description = systemCurrency.getDisplayName(forLanguageTag(getTenantSettings().getLocale()));

            log.info("Currency code: {}, description: {}", code, description);

            return new CurrencyDataDto(code, description);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("The input currency code is not a valid ISO 4217 code")) {
                log.warn("Código de moeda inválido: {}", currencyPair.getFirst());
            }
            return null;
        }
    }
}
