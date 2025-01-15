package br.dev.jstec.tyginvestiment.services.handlers.assets;

import br.dev.jstec.tyginvestiment.clients.alphaclient.dto.EtfProfileDto;
import br.dev.jstec.tyginvestiment.dto.assetstype.AssetDto;
import br.dev.jstec.tyginvestiment.dto.assetstype.FundDto;
import br.dev.jstec.tyginvestiment.enums.AssetMarketLocation;
import br.dev.jstec.tyginvestiment.enums.AssetType;
import br.dev.jstec.tyginvestiment.events.AssetSavedEvent;
import br.dev.jstec.tyginvestiment.exception.InfrastructureException;
import br.dev.jstec.tyginvestiment.repository.FundRepository;
import br.dev.jstec.tyginvestiment.services.handlers.AssetHistoryHandler;
import br.dev.jstec.tyginvestiment.services.mappers.AssetMapper;
import br.dev.jstec.tyginvestiment.services.strategy.AssetStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static br.dev.jstec.tyginvestiment.exception.ErrorMessage.ASSET_NOT_FOUND;
import static br.dev.jstec.tyginvestiment.exception.ErrorMessage.ATTRIBUTE_NOT_FOUND;
import static br.dev.jstec.tyginvestiment.services.validators.AssetBussinessRulesValidator.validateClientApiResponse;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Component("FUND")
@RequiredArgsConstructor
@Slf4j
public class FundHandler implements AssetStrategy {

    @Autowired
    private AssetMapper assetMapper;
    @Autowired
    private FundRepository fundRepository;
    @Autowired
    private br.dev.jstec.tyginvestiment.clients.alphaclient.AlphaClient alphaClient;
    @Autowired
    private CurrencyHandler currencyHandler;
    @Autowired
    private AssetHistoryHandler assetHistoryHandler;
    @Autowired
    private ApplicationEventPublisher publisher;

    @Value("${alpha-vantage.api-key}")
    private String apiKey;

    @Transactional(timeout = 300)
    public FundDto save(AssetMarketLocation marketLocation, List<String> symbols) {

        if (isNull(symbols) || symbols.isEmpty()) {
            throw new InfrastructureException(ATTRIBUTE_NOT_FOUND, "SIMBOLO");
        }

        var currency = AssetMarketLocation.getCurrency(marketLocation);

        if (isBlank(currency)) {
            throw new InfrastructureException(ATTRIBUTE_NOT_FOUND, "MOEDA");
        }

        var asset = getAsset(symbols.getFirst());

        if (isNull(asset)) {
            throw new InfrastructureException(ASSET_NOT_FOUND, symbols.getFirst());
        }

        currencyHandler.verifyAndSaveIfNotExists(currency);

        completeFundInfo(asset, symbols.getFirst(), currency);

        var entity = assetMapper.toEntity(asset);

        var entitySaved = fundRepository.save(entity);

        if (nonNull(entitySaved.getSymbol())) {
            publisher.publishEvent(new AssetSavedEvent(this, entitySaved));
        }

        return assetMapper.toDto(entitySaved);
    }


    @Transactional(readOnly = true)
    public FundDto findById(String symbol) {
        return fundRepository.findById(symbol)
                .map(assetMapper::toDto)
                .orElseThrow(() -> new InfrastructureException(ASSET_NOT_FOUND, symbol));
    }

    @Transactional
    public EtfProfileDto getAsset(String symbol) {

        var asset = alphaClient.getEtfProfile(symbol, apiKey);

        validateClientApiResponse(asset);

        if (isNull(asset) || isBlank(asset.getSymbol())) {
            throw new InfrastructureException(ASSET_NOT_FOUND, symbol);
        }

        return asset;
    }

    private void completeFundInfo(EtfProfileDto dto, String symbol, String Currency) {
        dto.setSymbol(symbol);
        dto.setAssetType(AssetType.FUND);
        dto.setName(symbol);
        dto.setCurrency(Currency);
    }


    @Override
    public AssetDto save(AssetMarketLocation marketLocation, String symbol) {
        return save(marketLocation, List.of(symbol));
    }
}
