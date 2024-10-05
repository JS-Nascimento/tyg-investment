package br.dev.jstec.tyginvestiment.clients.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class AlphaVantageInformation {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("Error Message")
    protected String errorMessage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("Information")
    protected String information;
}
