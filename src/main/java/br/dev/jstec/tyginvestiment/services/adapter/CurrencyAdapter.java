package br.dev.jstec.tyginvestiment.services.adapter;

import br.dev.jstec.tyginvestiment.clients.CurrencyClient;
import br.dev.jstec.tyginvestiment.clients.CurrencyLayerClient;
import br.dev.jstec.tyginvestiment.dto.CurrencyLayerResponse;
import br.dev.jstec.tyginvestiment.dto.ExchangeRateApiResponse;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CurrencyAdapter {

    private final CurrencyClient currencyClient;
    private final CurrencyLayerClient currencyLayerClient;

    @Value("${app.config.currency-base}")
    private String baseCurrency;

    public ExchangeRateApiResponse getLiveRates() {

        return currencyClient.getLiveExchangeRates();
    }

}