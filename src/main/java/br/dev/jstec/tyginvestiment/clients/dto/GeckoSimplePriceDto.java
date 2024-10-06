package br.dev.jstec.tyginvestiment.clients.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class GeckoSimplePriceDto {
    private Map<String, CurrencyData> currencies = new HashMap<>();

    @JsonAnySetter
    public void setCurrencyData(String name, CurrencyData currencyData) {
        this.currencies.put(name, currencyData);
    }

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
