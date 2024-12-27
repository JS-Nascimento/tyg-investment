package br.dev.jstec.tyginvestiment.services.handlers.assets;

import br.dev.jstec.tyginvestiment.clients.GeckoCoinClient;
import br.dev.jstec.tyginvestiment.clients.dto.CoinGeckoCriptoDto;
import br.dev.jstec.tyginvestiment.dto.assetstype.AssetDto;
import br.dev.jstec.tyginvestiment.dto.assetstype.CryptoDto;
import br.dev.jstec.tyginvestiment.enums.AssetMarketLocation;
import br.dev.jstec.tyginvestiment.enums.AssetType;
import br.dev.jstec.tyginvestiment.events.AssetSavedEvent;
import br.dev.jstec.tyginvestiment.exception.ErrorMessage;
import br.dev.jstec.tyginvestiment.exception.InfrastructureException;
import br.dev.jstec.tyginvestiment.repository.CryptoRepository;
import br.dev.jstec.tyginvestiment.services.handlers.AssetHistoryHandler;
import br.dev.jstec.tyginvestiment.services.mappers.AssetMapper;
import br.dev.jstec.tyginvestiment.services.strategy.AssetStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static br.dev.jstec.tyginvestiment.exception.ErrorMessage.ASSET_NOT_FOUND;
import static br.dev.jstec.tyginvestiment.exception.ErrorMessage.ATTRIBUTE_NOT_FOUND;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Component("CRYPTO")
@RequiredArgsConstructor
@Slf4j
public class CryptoHandler implements AssetStrategy {

    @Autowired
    private AssetMapper assetMapper;
    @Autowired
    private CryptoRepository cryptoRepository;
    @Autowired
    private GeckoCoinClient geckoCoinClient;
    @Autowired
    private CurrencyHandler currencyHandler;
    @Autowired
    private AssetHistoryHandler assetHistoryHandler;
    @Autowired
    private ApplicationEventPublisher publisher;

    @Transactional
    public CryptoDto save(String name, String currency) {

        if (isBlank(name)) {
            throw new InfrastructureException(ATTRIBUTE_NOT_FOUND, "NOME");
        }

        var asset = getAsset(name, currency);

        if (isNull(asset)) {
            throw new InfrastructureException(ASSET_NOT_FOUND, name);
        }

        currencyHandler.verifyAndSaveIfNotExists(currency);

        completeCryptoInfo(asset, currency);

        var entity = assetMapper.toEntity(asset);

        var entitySaved = cryptoRepository.save(entity);

        if (nonNull(entitySaved.getId())) {
            publisher.publishEvent(new AssetSavedEvent(this, entitySaved));
        }

        return assetMapper.toDto(entitySaved);
    }

    @Transactional(readOnly = true)
    public CryptoDto findById(String symbol) {
        return cryptoRepository.findById(symbol)
                .map(assetMapper::toDto)
                .orElseThrow(() -> new InfrastructureException(ErrorMessage.ASSET_NOT_FOUND, symbol));
    }


    @Transactional
    public CoinGeckoCriptoDto getAsset(String id, String currency) {

        var asset = geckoCoinClient.getCryptoMarketData(id.toLowerCase(), currency);

        if (isNull(asset) || asset.isEmpty()) {
            throw new InfrastructureException(ASSET_NOT_FOUND, id);
        }

        return asset.getFirst();
    }

    private void completeCryptoInfo(CoinGeckoCriptoDto dto, String currency) {
        dto.setId(dto.getId().toUpperCase());
        dto.setSymbol(dto.getSymbol().toUpperCase());
        dto.setAssetType(AssetType.CRYPTO);
        dto.setCurrency(currency);
    }

    @Override
    public <T extends AssetDto> T save(AssetMarketLocation marketLocation, String symbol) {
        return null;
    }
}
