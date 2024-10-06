package br.dev.jstec.tyginvestiment.services.handlers;

import br.dev.jstec.tyginvestiment.clients.GeckoCoinClient;
import br.dev.jstec.tyginvestiment.clients.dto.CoinGeckoCriptoDto;
import br.dev.jstec.tyginvestiment.dto.CurrencyDto;
import br.dev.jstec.tyginvestiment.dto.assetstype.CryptoDto;
import br.dev.jstec.tyginvestiment.enums.AssetType;
import br.dev.jstec.tyginvestiment.events.AssetSavedEvent;
import br.dev.jstec.tyginvestiment.models.Crypto;
import br.dev.jstec.tyginvestiment.repository.CryptoRepository;
import br.dev.jstec.tyginvestiment.services.mappers.AssetMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Currency;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Component("CRYPTO")
@RequiredArgsConstructor
@Slf4j
public class CryptoHandler implements AssetHandler<Crypto, CryptoDto> {

    private final AssetMapper mapper;
    private final CryptoRepository cryptoRepository;
    private final GeckoCoinClient geckoCoinClient;
    private final CurrencyHandler currencyHandler;
    private final AssetHistoryHandler assetHistoryHandler;
    private final ApplicationEventPublisher publisher;

    @Transactional
    @Override
    public CryptoDto save(String name, String currency) {

        if (isBlank(name)) {
            throw new RuntimeException("Name is required");
        }

        var asset = getAsset(name, currency);

        if (isNull(asset)) {
            throw new RuntimeException("Crypto not found");
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

        completeCryptoInfo(asset, currency);

        var entity = mapper.toEntity(asset);

        var entitySaved = cryptoRepository.saveAndFlush(entity);

        if (nonNull(entitySaved.getId())) {
            publisher.publishEvent(new AssetSavedEvent(this, entitySaved));
        }

        return mapper.toDto(entitySaved);
    }

    @Override
    @Transactional(readOnly = true)
    public CryptoDto findById(String symbol) {
        return cryptoRepository.findById(symbol)
                .map(mapper::toDto)
                .orElse(null);
    }

    @Override
    public CryptoDto save(CryptoDto dto) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public CryptoDto save(String symbol) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Transactional
    public CoinGeckoCriptoDto getAsset(String id, String currency) {

        var asset = geckoCoinClient.getCryptoMarketData(id.toLowerCase(), currency);

        if (isNull(asset) || asset.isEmpty()) {
            throw new RuntimeException("Cripto not found");
        }

        return asset.getFirst();
    }

    private void completeCryptoInfo(CoinGeckoCriptoDto dto, String currency) {
        dto.setId(dto.getId().toUpperCase());
        dto.setSymbol(dto.getSymbol().toUpperCase());
        dto.setAssetType(AssetType.CRYPTO);
        dto.setCurrency(currency);
    }
}
