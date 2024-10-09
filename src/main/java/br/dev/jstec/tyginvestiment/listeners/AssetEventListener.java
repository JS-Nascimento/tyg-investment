package br.dev.jstec.tyginvestiment.listeners;

import br.dev.jstec.tyginvestiment.events.AssetSavedEvent;
import br.dev.jstec.tyginvestiment.events.AssetTransactionSavedEvent;
import br.dev.jstec.tyginvestiment.models.Crypto;
import br.dev.jstec.tyginvestiment.models.Fund;
import br.dev.jstec.tyginvestiment.models.Stock;
import br.dev.jstec.tyginvestiment.services.handlers.AssetHistoryHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class AssetEventListener {

    private final AssetHistoryHandler assetHistoryHandler;

    @Async("taskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleAssetSavedEvent(AssetSavedEvent event) {
        log.info("Handling asset saved event for {}", event.getAsset().getSymbol());

        if (event.getAsset() instanceof Stock stock) {
            log.info("Stock found: {}", stock.getSymbol());
            assetHistoryHandler.getAssetHistory(stock.getSymbol());
        }

        if (event.getAsset() instanceof Fund fund) {
            log.info("Fund found: {}", fund.getSymbol());
            assetHistoryHandler.getAssetHistory(fund.getSymbol());
        }

        if (event.getAsset() instanceof Crypto crypto) {
            log.info("Crypto found: {}", crypto.getSymbol());
            assetHistoryHandler.getCryptoHistory(crypto.getSymbol(), crypto.getName(), crypto.getCurrency());
        }

        log.info("Finished handling asset saved event for {}", event.getAsset().getSymbol());
    }

    @Async("taskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleAssetTransactionEvent(AssetTransactionSavedEvent event) {
        log.info("Handling asset transaction update for {}", event.getTransaction().getAsset().getSymbol());

        if (event.getTransaction().getAsset() instanceof Stock stock) {
            log.info("Stock found: {}", stock.getSymbol());
            assetHistoryHandler.getAssetHistory(stock.getSymbol());
        }

        if (event.getTransaction().getAsset() instanceof Fund fund) {
            log.info("Fund found: {}", fund.getSymbol());
            assetHistoryHandler.getAssetHistory(fund.getSymbol());
        }

        if (event.getTransaction().getAsset() instanceof Crypto crypto) {
            log.info("Crypto found: {}", crypto.getSymbol());
            assetHistoryHandler.getCryptoHistory(crypto.getSymbol(), crypto.getName(), crypto.getCurrency());
        }

        log.info("Finished handling asset saved event for {}", event.getTransaction().getAsset().getSymbol());
    }
}
