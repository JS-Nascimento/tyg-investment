package br.dev.jstec.tyginvestiment.clients;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient(name = "alphaClient", url = "${alpha-vantage.base-url}")
public interface AlphaClient {

    @GetMapping("${alpha-vantage.get-info-assets}symbol={symbol}&apikey=${alpha-vantage.api-key}")
    @Cacheable("exchange-rates")
    AlphaVantageClient getAssetInfo(@RequestParam("symbol") String symbol);

    @GetMapping("${alpha-vantage.get-daily-time-series}symbol={symbol}&apikey=${alpha-vantage.api-key}")
    @Cacheable("exchange-rates")
    AlphaVantageTimeSeriesDto getAssetHistory(@RequestParam("symbol") String symbol);
}
