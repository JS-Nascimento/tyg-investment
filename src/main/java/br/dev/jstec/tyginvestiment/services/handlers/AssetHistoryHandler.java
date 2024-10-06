package br.dev.jstec.tyginvestiment.services.handlers;

import br.dev.jstec.tyginvestiment.clients.AlphaClient;
import br.dev.jstec.tyginvestiment.clients.GeckoCoinClient;
import br.dev.jstec.tyginvestiment.config.ApiKeyManager;
import br.dev.jstec.tyginvestiment.models.StockQuotation;
import br.dev.jstec.tyginvestiment.repository.AssetRepository;
import br.dev.jstec.tyginvestiment.repository.StockQuotationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.text.MessageFormat.format;
import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
@Slf4j
public class AssetHistoryHandler {

    private final AlphaClient alphaClient;
    private final GeckoCoinClient geckoCoinClient;
    private final StockQuotationRepository stockQuotationRepository;
    private final AssetRepository assetRepository;

    private final ApiKeyManager apiKeyManager;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CompletableFuture<Void> getAssetHistory(String symbol) {

        log.info("Getting asset history for {}", symbol);

        var asset = assetRepository.findBySymbol(symbol)
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        var history = alphaClient.getAssetHistory(asset.getSymbol(), apiKeyManager.getAvailableApiKey());

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
                    quotation.setAsset(asset);
                    quotation.setDate(entry.getKey().atTime(LocalTime.now()));
                    quotation.setOpen(entry.getValue().getOpen());
                    quotation.setHigh(entry.getValue().getHigh());
                    quotation.setLow(entry.getValue().getLow());
                    quotation.setClose(entry.getValue().getClose());
                    quotation.setVolume(Double.valueOf(entry.getValue().getVolume()));
                    return quotation;
                })
                .toList();

        stockQuotationRepository.saveAll(quotations);

        log.info("Asset history saved for {}", asset.getSymbol());

        return CompletableFuture.completedFuture(null);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CompletableFuture<Void> getCryptoHistory(String symbol, String name, String currency) {

        log.info("Getting asset history for {}", symbol);

        var asset = assetRepository.findBySymbol(symbol)
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        var history = geckoCoinClient.getCryptoTimeSeries(name.toLowerCase(), currency);

        if (history.getPrices().isEmpty()) {
            throw new RuntimeException("Crypto History not found");
        }

        List<StockQuotation> quotations = new ArrayList<>();

        for (int i = 0; i < history.getPrices().size(); i++) {
            var quotation = new StockQuotation();
            quotation.setAsset(asset);
            quotation.setDate(Instant.ofEpochMilli(history.getPrices().get(i).getTimestamp())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime());
            quotation.setOpen(BigDecimal.valueOf(history.getPrices().get(i).getValue()));
            quotation.setHigh(BigDecimal.valueOf(history.getPrices().get(i).getValue()));
            quotation.setLow(BigDecimal.valueOf(history.getPrices().get(i).getValue()));
            quotation.setClose(BigDecimal.valueOf(history.getPrices().get(i).getValue()));
            quotation.setMarketCaps(history.getMarketCaps().get(i).getValue());
            quotation.setVolume(history.getTotalVolumes().get(i).getValue());
            quotations.add(quotation);
        }

        stockQuotationRepository.saveAll(quotations);

        log.info("Crypto history saved for {}", asset.getSymbol());

        return CompletableFuture.completedFuture(null);
    }
}
