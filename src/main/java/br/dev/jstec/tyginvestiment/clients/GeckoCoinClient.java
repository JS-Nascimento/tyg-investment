package br.dev.jstec.tyginvestiment.clients;

import br.dev.jstec.tyginvestiment.clients.dto.CoinGeckoCriptoDto;
import br.dev.jstec.tyginvestiment.clients.dto.GeckoSimplePriceDto;
import br.dev.jstec.tyginvestiment.clients.dto.GeckoTimeSeriesDto;
import feign.Feign;
import feign.Logger;
import feign.RequestInterceptor;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "geckoCoinClient", url = "${coin-gecko.base-url}", configuration = GeckoCoinClient.GekcoCoinClientConfig.class)
public interface GeckoCoinClient {

    @GetMapping("${coin-gecko.get-coin-market-data}")
    @Cacheable("crypto-market-data")
    List<CoinGeckoCriptoDto> getCryptoMarketData(@RequestParam("ids") String ids,
                                                 @RequestParam("vs_currency") String vsCurrency);

    @GetMapping("${coin-gecko.get-coin-time-series}")
    @Cacheable("crypto-time-series")
    GeckoTimeSeriesDto getCryptoTimeSeries(@PathVariable("id") String id,
                                           @RequestParam("vs_currency") String vsCurrency);

    @GetMapping("${coin-gecko.get-coin-simple-price}")
    @Cacheable("crypto-time-series")
    GeckoSimplePriceDto getCryptoSimplePrice(@RequestParam("ids") String ids,
                                             @RequestParam("vs_currencies") String vsCurrencies);


    class GekcoCoinClientConfig {

        @Value("${coin-gecko.api-key}")
        private String apiKey;

        @Bean
        public Feign.Builder feignBuilder() {
            return Feign.builder()
                    .logger(new Slf4jLogger(GeckoCoinClient.class))
                    .logLevel(Logger.Level.FULL);
        }

        @Bean
        public RequestInterceptor requestInterceptor() {
            return requestTemplate -> {
                requestTemplate.header("x-cg-demo-api-key", apiKey);
            };
        }
    }
}
