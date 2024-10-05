package br.dev.jstec.tyginvestiment.clients;

import br.dev.jstec.tyginvestiment.clients.dto.AlphaVantageClient;
import br.dev.jstec.tyginvestiment.clients.dto.AlphaVantageTimeSeriesDto;
import br.dev.jstec.tyginvestiment.clients.dto.EtfProfileDto;
import br.dev.jstec.tyginvestiment.clients.dto.GlobalQuoteResponseDto;
import br.dev.jstec.tyginvestiment.config.FeignConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "alphaClient", url = "${alpha-vantage.base-url}", configuration = FeignConfig.class)
public interface AlphaClient {

    @GetMapping("${alpha-vantage.get-info-assets}")
    @Cacheable("exchange-rates")
    AlphaVantageClient getAssetInfo(@RequestParam("symbol") String symbol, @RequestParam("apikey") String apiKey);

    @GetMapping("${alpha-vantage.get-daily-time-series}")
    @Cacheable("exchange-time-series")
    AlphaVantageTimeSeriesDto getAssetHistory(@RequestParam("symbol") String symbol, @RequestParam("apikey") String apiKey);

    @GetMapping("${alpha-vantage.get-global-quote}")
    @Cacheable("exchange-global-quote")
    GlobalQuoteResponseDto getGlobalQuote(@RequestParam("symbol") String symbol, @RequestParam("apikey") String apiKey);

    @GetMapping("${alpha-vantage.get-etf-profile}")
    @Cacheable("exchange-etf-profile")
    EtfProfileDto getEtfProfile(@RequestParam("symbol") String symbol, @RequestParam("apikey") String apiKey);
}
