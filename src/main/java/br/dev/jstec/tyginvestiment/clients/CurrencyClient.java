package br.dev.jstec.tyginvestiment.clients;

import br.dev.jstec.tyginvestiment.dto.ExchangeRateApiResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "currencyClient", url = "${exchangerate.base-url}")
public interface CurrencyClient {

    @GetMapping("${exchangerate.rate-url}/{baseCurrency}")
    @Cacheable("exchange-rates")
    ExchangeRateApiResponse getLiveExchangeRates(@PathVariable("baseCurrency") String baseCurrency);

    @GetMapping(value = "${exchangerate.list-url}", consumes = "application/json")
    Object getExchangeList();
}

