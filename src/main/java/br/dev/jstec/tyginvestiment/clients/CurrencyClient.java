package br.dev.jstec.tyginvestiment.clients;

import br.dev.jstec.tyginvestiment.dto.ExchangeRateApiResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "currencyClient", url = "${exchangerate.base-url}")
public interface CurrencyClient {

    @GetMapping
    @Cacheable("exchange-rates")
    ExchangeRateApiResponse getLiveExchangeRates();
}
