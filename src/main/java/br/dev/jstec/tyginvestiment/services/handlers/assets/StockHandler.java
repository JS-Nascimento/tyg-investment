package br.dev.jstec.tyginvestiment.services.handlers.assets;

import br.dev.jstec.tyginvestiment.clients.alphaclient.AlphaClient;
import br.dev.jstec.tyginvestiment.clients.brapi.BrapiClient;
import br.dev.jstec.tyginvestiment.clients.brapi.BrapiModuleOptions;
import br.dev.jstec.tyginvestiment.dto.assetstype.AssetDto;
import br.dev.jstec.tyginvestiment.dto.assetstype.BrapiAssetDto;
import br.dev.jstec.tyginvestiment.dto.assetstype.StockDto;
import br.dev.jstec.tyginvestiment.enums.AssetMarketLocation;
import br.dev.jstec.tyginvestiment.enums.AssetType;
import br.dev.jstec.tyginvestiment.events.AssetSavedEvent;
import br.dev.jstec.tyginvestiment.exception.InfrastructureException;
import br.dev.jstec.tyginvestiment.repository.BrapiAssetRepository;
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
import static br.dev.jstec.tyginvestiment.clients.brapi.BrapiRangeOptions.ONE_YEAR;
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
    private BrapiAssetRepository brapiAssetRepository;

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
    public AssetDto save(AssetMarketLocation marketLocation, String symbol) {
        return save(marketLocation, List.of(symbol));
    }

    @Transactional
    @Override
    public AssetDto save(AssetMarketLocation marketLocation, List<String> symbols) {

        return switch (marketLocation) {
            case BR -> saveBRAsset(symbols);
            case US -> saveUSAsset(symbols);
            default -> throw new InfrastructureException(ATTRIBUTE_NOT_FOUND, "marketLocation");
        };
    }


    @Transactional(readOnly = true)
    public StockDto findById(String symbol) {
        return stockRepository.findById(symbol)
                .map(assetMapper::toDto)
                .orElseThrow(() -> new InfrastructureException(ASSET_NOT_FOUND, symbol));
    }

    private BrapiAssetDto saveBRAsset(List<String> symbols) {

        var modules = new String[]{BrapiModuleOptions.BALANCE_SHEET.getValue(),
                BrapiModuleOptions.INCOME_STATEMENT.getValue(), BrapiModuleOptions.DEFAULT_KEY_STATISTICS.getValue()};

        var brapi = brapiClient.getAssetInfo(symbols, brapiToken, ONE_YEAR.getValue(), ONE_DAY.getValue(), true, true, modules);

        if (isNull(brapi)) {
            throw new InfrastructureException(ASSET_NOT_FOUND, symbols.toString());
        }

        var entity = assetMapper.toEntity(brapi.getResults().getFirst());
        entity.setAssetType(AssetType.STOCK);

        var assetSaved = brapiAssetRepository.save(entity);

        if (nonNull(assetSaved.getSymbol())) {
            publisher.publishEvent(new AssetSavedEvent(this, assetSaved));
        }

        return assetMapper.toDto(assetSaved);
    }

    private StockDto saveUSAsset(List<String> symbols) {

        var stock = alphaClient.getAssetInfo(symbols.getFirst(), alphaVantageApiKey);
        validateClientApiResponse(stock);

        if (isNull(stock)) {
            throw new InfrastructureException(ASSET_NOT_FOUND, symbols.toString());
        }

        var assetSaved = stockRepository.save(assetMapper.toEntity(stock));

        if (nonNull(assetSaved.getSymbol())) {
            publisher.publishEvent(new AssetSavedEvent(this, assetSaved));
        }

        return assetMapper.toDto(assetSaved);
    }
}
