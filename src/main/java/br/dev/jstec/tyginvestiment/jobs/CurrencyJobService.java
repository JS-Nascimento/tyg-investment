package br.dev.jstec.tyginvestiment.jobs;

import br.dev.jstec.tyginvestiment.services.handlers.CurrencyHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CurrencyJobService {

    private final CurrencyHandler currencyHandler;

    @Scheduled(cron = "0 0/10 * * * ?")
    @Async
    public void updateCurrencyRateConversion() {
        currencyHandler.updateCurrency();
        log.info("Atualização de taxa de conversão executado de forma assíncrona: " + Thread.currentThread().getName());
    }
}
