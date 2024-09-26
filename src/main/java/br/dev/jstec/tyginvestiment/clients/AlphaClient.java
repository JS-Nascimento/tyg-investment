package br.dev.jstec.tyginvestiment.clients;

import br.dev.jstec.tyginvestiment.dto.ExchangeRateApiResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "alphaClient", url = "${exchangerate.base-url}")
public interface AlphaClient {

    @GetMapping
    @Cacheable("exchange-rates")
    ExchangeRateApiResponse getLiveExchangeRates();
}