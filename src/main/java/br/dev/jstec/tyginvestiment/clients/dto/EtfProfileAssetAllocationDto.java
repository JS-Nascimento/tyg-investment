package br.dev.jstec.tyginvestiment.clients.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EtfProfileAssetAllocationDto {
    @JsonProperty("domestic_equities")
    private Double domesticEquities;

    @JsonProperty("foreign_equities")
    private Double foreignEquities;

    @JsonProperty("bond")
    private Double bond;

    @JsonProperty("cash")
    private Double cash;

    @JsonProperty("other")
    private Double other;
}
