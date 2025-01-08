package br.dev.jstec.tyginvestiment.services.handlers.assets;

import br.dev.jstec.tyginvestiment.clients.alphaclient.AlphaClient;
import br.dev.jstec.tyginvestiment.clients.brapi.BrapiClient;
import br.dev.jstec.tyginvestiment.dto.assetstype.StockDto;
import br.dev.jstec.tyginvestiment.enums.AssetMarketLocation;
import br.dev.jstec.tyginvestiment.events.AssetSavedEvent;
import br.dev.jstec.tyginvestiment.exception.InfrastructureException;
import br.dev.jstec.tyginvestiment.models.Stock;
import br.dev.jstec.tyginvestiment.repository.StockRepository;
import br.dev.jstec.tyginvestiment.services.handlers.AssetHistoryHandler;
import br.dev.jstec.tyginvestiment.services.mappers.AssetMapper;
import br.dev.jstec.tyginvestiment.services.strategy.AssetStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static br.dev.jstec.tyginvestiment.clients.brapi.BrapiIntervalOptions.ONE_DAY;
import static br.dev.jstec.tyginvestiment.clients.brapi.BrapiRangeOptions.THREE_MONTHS;
import static br.dev.jstec.tyginvestiment.exception.ErrorMessage.ASSET_NOT_FOUND;
import static br.dev.jstec.tyginvestiment.exception.ErrorMessage.ATTRIBUTE_NOT_FOUND;
import static br.dev.jstec.tyginvestiment.services.validators.AssetBussinessRulesValidator.validateClientApiResponse;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component("STOCK")
@Slf4j
public class StockHandler implements AssetStrategy {

    @Autowired
    private AssetMapper assetMapper;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private AlphaClient alphaClient;

    @Autowired
    private BrapiClient brapiClient;

    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private AssetHistoryHandler assetHistoryHandler;

    @Value("${alpha-vantage.api-key}")
    private String alphaVantageApiKey;

    @Value("${brapi-api.token}")
    private String brapiToken;

    @Override
    @Transactional
    public StockDto save(AssetMarketLocation marketLocation, List<String> symbols) {

        var asset = getAsset(marketLocation, symbols);

        if (isNull(asset)) {
            throw new InfrastructureException(ASSET_NOT_FOUND, symbols.toString());
        }

        var entitySaved = stockRepository.save(asset);

        if (nonNull(entitySaved.getSymbol())) {
            publisher.publishEvent(new AssetSavedEvent(this, entitySaved));
        }

        return assetMapper.toDto(entitySaved);
    }


    @Transactional(readOnly = true)
    public StockDto findById(String symbol) {
        return stockRepository.findById(symbol)
                .map(assetMapper::toDto)
                .orElseThrow(() -> new InfrastructureException(ASSET_NOT_FOUND, symbol));
    }

    private Stock getAsset(AssetMarketLocation marketLocation, List<String> symbols) {

        switch (marketLocation) {
            case BR:
                return brapiClient.getAssetInfo(symbols, brapiToken, THREE_MONTHS, ONE_DAY, true, false, List.of());
            case US:
                var asset = alphaClient.getAssetInfo(symbols.getFirst(), alphaVantageApiKey);
                validateClientApiResponse(asset);
                return assetMapper.toEntity(asset);
            default:
                throw new InfrastructureException(ATTRIBUTE_NOT_FOUND, "marketLocation");
        }
    }
}
