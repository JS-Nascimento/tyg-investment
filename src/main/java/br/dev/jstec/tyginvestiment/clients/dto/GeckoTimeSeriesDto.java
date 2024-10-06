package br.dev.jstec.tyginvestiment.clients.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GeckoTimeSeriesDto {
    @JsonProperty("prices")
    private List<TimeValuePair> prices;

    @JsonProperty("market_caps")
    private List<TimeValuePair> marketCaps;

    @JsonProperty("total_volumes")
    private List<TimeValuePair> totalVolumes;

    @Data
    public static class TimeValuePair {

        private Long timestamp;
        private Double value;

        @JsonCreator
        public TimeValuePair(List<Object> list) {
            if (list != null && list.size() == 2) {
                this.timestamp = ((Number) list.get(0)).longValue();
                this.value = ((Number) list.get(1)).doubleValue();
            }
        }
    }
}
