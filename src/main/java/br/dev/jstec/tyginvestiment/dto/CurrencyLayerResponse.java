package br.dev.jstec.tyginvestiment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Data
@NoArgsConstructor
public class CurrencyLayerResponse {

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("terms")
    private String terms;

    @JsonProperty("privacy")
    private String privacy;

    @JsonProperty("timestamp")
    private Long timestamp;

    @JsonProperty("source")
    private String source;

    @JsonProperty("quotes")
    private Map<String, Double> quotes;

    @JsonProperty("error")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ErrorResponse error;

    @Getter
    @Data
    @Setter
    @NoArgsConstructor
    public static class ErrorResponse {
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private int code;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String info;

        private String type;
    }
}
