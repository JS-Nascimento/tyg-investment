package br.dev.jstec.tyginvestiment.services.handlers;

import br.dev.jstec.tyginvestiment.clients.AlphaClient;
import br.dev.jstec.tyginvestiment.clients.GeckoCoinClient;
import br.dev.jstec.tyginvestiment.config.ApiKeyManager;
import br.dev.jstec.tyginvestiment.models.Asset;
import br.dev.jstec.tyginvestiment.models.StockQuotation;
import br.dev.jstec.tyginvestiment.repository.StockQuotationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

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

    private final ApiKeyManager apiKeyManager;

    @Async("taskExecutor")
    public CompletableFuture<Void> getAssetHistory(Asset asset) {

        log.info("Getting asset history for {}", asset.getSymbol());

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

        stockQuotationRepository.saveAllAndFlush(quotations);

        log.info("Asset history saved for {}", asset.getSymbol());

        return CompletableFuture.completedFuture(null);
    }

    @Async("taskExecutor")
    public CompletableFuture<Void> getCryptoHistory(Asset asset) {

        log.info("Getting asset history for {}", asset.getSymbol());

        var history = geckoCoinClient.getCryptoTimeSeries(asset.getName().toLowerCase(), asset.getCurrency());

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

        stockQuotationRepository.saveAllAndFlush(quotations);

        log.info("Crypto history saved for {}", asset.getSymbol());

        return CompletableFuture.completedFuture(null);
    }
}
