package br.dev.jstec.tyginvestiment.services.handlers;

import br.dev.jstec.tyginvestiment.clients.AlphaClient;
import br.dev.jstec.tyginvestiment.config.ApiKeyManager;
import br.dev.jstec.tyginvestiment.models.Asset;
import br.dev.jstec.tyginvestiment.models.StockQuotation;
import br.dev.jstec.tyginvestiment.repository.StockQuotationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

import static java.text.MessageFormat.format;
import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
@Slf4j
public class AssetHistoryHandler {

    private final AlphaClient alphaClient;
    private final StockQuotationRepository stockQuotationRepository;

    private final ApiKeyManager apiKeyManager;

    @Async("taskExecutor")
    public CompletableFuture<Void> getAssetHistory(Asset fund) {

        log.info("Getting asset history for {}", fund.getSymbol());

        var history = alphaClient.getAssetHistory(fund.getSymbol(), apiKeyManager.getAvailableApiKey());

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
                    quotation.setStock(fund);
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

        log.info("Asset history saved for {}", fund.getSymbol());

        return CompletableFuture.completedFuture(null);
    }
}
