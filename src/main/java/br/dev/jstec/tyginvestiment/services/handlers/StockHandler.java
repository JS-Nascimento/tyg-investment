package br.dev.jstec.tyginvestiment.services.handlers;

import br.dev.jstec.tyginvestiment.clients.AlphaClient;
import br.dev.jstec.tyginvestiment.clients.AlphaVantageClient;
import br.dev.jstec.tyginvestiment.dto.assetstype.StockDto;
import br.dev.jstec.tyginvestiment.models.Stock;
import br.dev.jstec.tyginvestiment.models.StockQuotation;
import br.dev.jstec.tyginvestiment.repository.StockQuotationRepository;
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
    private final StockQuotationRepository stockQuotationRepository;
    private final AlphaClient alphaClient;

    @Override
    @Transactional
    public StockDto save(String symbol) {

        if (isBlank(symbol)) {
            throw new RuntimeException("Symbol is required");
        }

        var asset = getAsset(symbol);

        var entity = mapper.toEntity(asset);

        var entitySaved = stockRepository.save(entity);

        if(nonNull(entitySaved.getSymbol())) {
            getAssetHistory(entitySaved);
        }

        return mapper.toDto(entitySaved);
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
        return null;
    }

    @Transactional
    public AlphaVantageClient getAsset(String symbol) {

        var asset = alphaClient.getAssetInfo(symbol);

        if (nonNull(asset.getInformation()) ) {
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

    private void getAssetHistory(Stock stock) {
        var history = alphaClient.getAssetHistory(stock.getSymbol());

        if (nonNull(history.getInformation())) {
            throw new RuntimeException(format("Error getting asset history with {0}", history.getInformation()));
        }
        if (nonNull(history.getErrorMessage())) {
            throw new RuntimeException(format("Error getting asset history with {0}", history.getErrorMessage()));
        }

        if (history.getTimeSeriesDaily().isEmpty()) {
            throw new RuntimeException("Asset History not found");
        }

        var quotations = history.getTimeSeriesDaily().entrySet().stream()
                .map(entry -> {
                    var quotation = new StockQuotation();
                    quotation.setStock(stock);
                    quotation.setDate(entry.getKey());
                    quotation.setOpen(entry.getValue().getOpen());
                    quotation.setHigh(entry.getValue().getHigh());
                    quotation.setLow(entry.getValue().getLow());
                    quotation.setClose(entry.getValue().getClose());
                    quotation.setVolume(entry.getValue().getVolume());
                    return quotation;
                })
                .toList();

        stockQuotationRepository.saveAllAndFlush(quotations);
    }

}
