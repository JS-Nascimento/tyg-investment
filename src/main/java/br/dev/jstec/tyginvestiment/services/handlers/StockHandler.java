package br.dev.jstec.tyginvestiment.services.handlers;

import br.dev.jstec.tyginvestiment.clients.AlphaClient;
import br.dev.jstec.tyginvestiment.clients.dto.AlphaVantageClient;
import br.dev.jstec.tyginvestiment.config.ApiKeyManager;
import br.dev.jstec.tyginvestiment.dto.assetstype.StockDto;
import br.dev.jstec.tyginvestiment.models.Stock;
import br.dev.jstec.tyginvestiment.repository.StockRepository;
import br.dev.jstec.tyginvestiment.services.mappers.AssetMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static java.text.MessageFormat.format;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Component("STOCK")
@RequiredArgsConstructor
@Slf4j
public class StockHandler implements AssetHandler<Stock, StockDto> {

    private final AssetMapper mapper;
    private final StockRepository stockRepository;
    private final AlphaClient alphaClient;

    private final AssetHistoryHandler assetHistoryHandler;

    private final ApiKeyManager apiKeyManager;

    @Override
    @Transactional
    public StockDto save(String symbol) {

        if (isBlank(symbol)) {
            throw new RuntimeException("Symbol is required");
        }

        var asset = getAsset(symbol);

        var entity = mapper.toEntity(asset);

        var entitySaved = stockRepository.save(entity);

        if (nonNull(entitySaved.getSymbol())) {
            assetHistoryHandler.getAssetHistory(entitySaved);
        }

        return mapper.toDto(entitySaved);
    }

    @Override
    public StockDto save(String symbol, String currency) {
        throw new UnsupportedOperationException("Stock does not have currency");
    }

    @Override
    @Transactional(readOnly = true)
    public StockDto findById(String symbol) {
        return stockRepository.findById(symbol)
                .map(mapper::toDto)
                .orElse(null);
    }

    @Override
    public StockDto save(StockDto dto) {
        throw new UnsupportedOperationException("Stock does not have currency");
    }

    @Transactional
    public AlphaVantageClient getAsset(String symbol) {

        var asset = alphaClient.getAssetInfo(symbol, apiKeyManager.getAvailableApiKey());

        if (nonNull(asset.getInformation())) {
            throw new RuntimeException(format("Error getting asset information with {0}", asset.getInformation()));
        }
        if (nonNull(asset.getErrorMessage())) {
            throw new RuntimeException(format("Error getting asset information with {0}", asset.getErrorMessage()));
        }

        if (isNull(asset) || isBlank(asset.getSymbol())) {
            throw new RuntimeException("Asset not found");
        }

        return asset;
    }

}
