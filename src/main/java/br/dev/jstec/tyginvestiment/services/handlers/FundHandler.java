package br.dev.jstec.tyginvestiment.services.handlers;

import br.dev.jstec.tyginvestiment.clients.AlphaClient;
import br.dev.jstec.tyginvestiment.clients.dto.EtfProfileDto;
import br.dev.jstec.tyginvestiment.config.ApiKeyManager;
import br.dev.jstec.tyginvestiment.dto.CurrencyDto;
import br.dev.jstec.tyginvestiment.dto.assetstype.FundDto;
import br.dev.jstec.tyginvestiment.enums.AssetType;
import br.dev.jstec.tyginvestiment.models.Fund;
import br.dev.jstec.tyginvestiment.repository.FundRepository;
import br.dev.jstec.tyginvestiment.services.mappers.AssetMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Currency;

import static java.text.MessageFormat.format;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Component("FUND")
@RequiredArgsConstructor
@Slf4j
public class FundHandler implements AssetHandler<Fund, FundDto> {

    private final AssetMapper mapper;
    private final FundRepository fundRepository;
    private final AlphaClient alphaClient;
    private final CurrencyHandler currencyHandler;
    private final AssetHistoryHandler assetHistoryHandler;

    private final ApiKeyManager apiKeyManager;

    @Transactional
    @Override
    public FundDto save(String symbol, String currency) {

        if (isBlank(symbol)) {
            throw new RuntimeException("Symbol is required");
        }

        var asset = getAsset(symbol);

        if (isNull(asset)) {
            throw new RuntimeException("ETF not found");
        }

        if (!currencyHandler.exists(currency)) {
            var newCurrency = Currency.getInstance(currency);
            var currencyDto = new CurrencyDto();
            currencyDto.setCode(newCurrency.getCurrencyCode());
            currencyDto.setName(newCurrency.getDisplayName());
            currencyDto.setSymbol(newCurrency.getSymbol());
            currencyDto.setDecimalPlaces(currencyHandler.getDecimalPlaces());
            currencyDto.setCurrencyBase(false);

            currencyHandler.saveCurrency(currencyDto);
        }

        completeFundInfo(asset, symbol, currency);

        var entity = mapper.toEntity(asset);

        var entitySaved = fundRepository.save(entity);

        if (nonNull(entitySaved.getSymbol())) {
            assetHistoryHandler.getAssetHistory(entitySaved);
        }

        return mapper.toDto(entitySaved);
    }

    @Override
    @Transactional(readOnly = true)
    public FundDto findById(String symbol) {
        return fundRepository.findById(symbol)
                .map(mapper::toDto)
                .orElse(null);
    }

    @Override
    public FundDto save(FundDto dto) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public FundDto save(String symbol) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Transactional
    public EtfProfileDto getAsset(String symbol) {

        var asset = alphaClient.getEtfProfile(symbol, apiKeyManager.getAvailableApiKey());

        if (nonNull(asset.getInformation())) {
            throw new RuntimeException(format("Error getting asset information with {0}", asset.getInformation()));
        }
        if (nonNull(asset.getErrorMessage())) {
            throw new RuntimeException(format("Error getting asset information with {0}", asset.getErrorMessage()));
        }

        if (isNull(asset)) {
            throw new RuntimeException("ETF not found");
        }

        return asset;
    }

    private void completeFundInfo(EtfProfileDto dto, String symbol, String Currency) {
        dto.setSymbol(symbol);
        dto.setAssetType(AssetType.FUND);
        dto.setName(symbol);
        dto.setCurrency(Currency);
    }
}
