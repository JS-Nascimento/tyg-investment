package br.dev.jstec.tyginvestiment.services.handlers;

import br.dev.jstec.tyginvestiment.clients.AlphaClient;
import br.dev.jstec.tyginvestiment.clients.dto.AlphaVantageClient;
import br.dev.jstec.tyginvestiment.dto.assetstype.StockDto;
import br.dev.jstec.tyginvestiment.events.AssetSavedEvent;
import br.dev.jstec.tyginvestiment.exception.InfrastructureException;
import br.dev.jstec.tyginvestiment.models.Stock;
import br.dev.jstec.tyginvestiment.repository.StockRepository;
import br.dev.jstec.tyginvestiment.services.mappers.AssetMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static br.dev.jstec.tyginvestiment.exception.ErrorMessage.ASSET_NOT_FOUND;
import static br.dev.jstec.tyginvestiment.exception.ErrorMessage.ATTRIBUTE_NOT_FOUND;
import static br.dev.jstec.tyginvestiment.services.validators.AssetBussinessRulesValidator.validateClientApiResponse;
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
    private final ApplicationEventPublisher publisher;

    private final AssetHistoryHandler assetHistoryHandler;

    @Value("${alpha-vantage.api-key}")
    private String apiKey;

    @Override
    @Transactional
    public StockDto save(String symbol) {

        if (isBlank(symbol)) {
            throw new InfrastructureException(ATTRIBUTE_NOT_FOUND, symbol);
        }

        var asset = getAsset(symbol);

        if (isNull(asset)) {
            throw new InfrastructureException(ASSET_NOT_FOUND, symbol);
        }

        var entity = mapper.toEntity(asset);

        var entitySaved = stockRepository.save(entity);

        if (nonNull(entitySaved.getSymbol())) {
            publisher.publishEvent(new AssetSavedEvent(this, entitySaved));
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
                .orElseThrow(() -> new InfrastructureException(ASSET_NOT_FOUND, symbol));
    }

    @Override
    public StockDto save(StockDto dto) {
        throw new UnsupportedOperationException("Stock does not have currency");
    }

    @Transactional
    public AlphaVantageClient getAsset(String symbol) {

        var asset = alphaClient.getAssetInfo(symbol, apiKey);

        validateClientApiResponse(asset);

        if (isNull(asset) || isBlank(asset.getSymbol())) {
            throw new InfrastructureException(ASSET_NOT_FOUND, symbol);
        }

        return asset;
    }

}
