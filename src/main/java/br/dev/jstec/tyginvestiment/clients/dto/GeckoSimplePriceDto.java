package br.dev.jstec.tyginvestiment.clients.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class GeckoSimplePriceDto {
    @JsonProperty
    private Map<String, CurrencyData> currencies;

    @Data
    public static class CurrencyData {
        @JsonProperty("usd")
        private Double usd;

        @JsonProperty("usd_market_cap")
        private Double usdMarketCap;

        @JsonProperty("usd_24h_vol")
        private Double usd24hVol;

        @JsonProperty("usd_24h_change")
        private Double usd24hChange;

        @JsonProperty("last_updated_at")
        private Long lastUpdatedAt;
    }
}
