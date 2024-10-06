package br.dev.jstec.tyginvestiment.jobs;

import br.dev.jstec.tyginvestiment.services.handlers.StockQuotationHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AssetJobService {

    private final StockQuotationHandler handler;

    //    @Scheduled(cron = "0 0/1 * * * ?")
    @Scheduled(cron = "0 0 20 ? * MON-FRI")
    @Async
    public void updateCurrencyRateConversion() {
        handler.updateQuotations();
        log.info("Cotações atualizadas com sucesso");
    }
}
