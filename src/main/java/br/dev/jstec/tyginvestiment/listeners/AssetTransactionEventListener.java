package br.dev.jstec.tyginvestiment.listeners;

import br.dev.jstec.tyginvestiment.events.AssetTransactionSavedEvent;
import br.dev.jstec.tyginvestiment.services.handlers.AccountHandler;
import br.dev.jstec.tyginvestiment.services.handlers.AccountHistoryHandler;
import br.dev.jstec.tyginvestiment.services.handlers.AccountHoldingHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class AssetTransactionEventListener {

    private final AccountHandler accountHandler;
    private final AccountHoldingHandler accountHoldingHandler;
    private final AccountHistoryHandler accountHistoryHandler;


    @Async("taskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleAssetTransactionSavedEvent(AssetTransactionSavedEvent event) {
        log.info("Handling transaction saved event for {}", event.getTransaction().getAsset().getSymbol());

        accountHoldingHandler.updateHoldingAfterTransaction(event.getTransaction());
        accountHandler.updateAccountAfterTransaction(event.getTransaction());
        accountHistoryHandler.createAccountHistoryByTransaction(event.getTransaction());

        log.info("Finished handling transaction saved event for {}", event.getTransaction().getAsset().getSymbol());
    }
}
