package br.dev.jstec.tyginvestiment.services.adapter;

import br.dev.jstec.tyginvestiment.clients.CurrencyClient;
import br.dev.jstec.tyginvestiment.clients.dto.CurrencyLayerClient;
import br.dev.jstec.tyginvestiment.dto.ExchangeRateApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CurrencyAdapter {

    private final CurrencyClient currencyClient;
    private final CurrencyLayerClient currencyLayerClient;

    public ExchangeRateApiResponse getLiveRates(String baseCurrency) {

        return currencyClient.getLiveExchangeRates(baseCurrency);
    }

}