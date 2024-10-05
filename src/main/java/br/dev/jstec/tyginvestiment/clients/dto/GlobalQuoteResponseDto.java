package br.dev.jstec.tyginvestiment.clients.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GlobalQuoteResponseDto {

    @JsonProperty("Global Quote")
    private GlobalQuoteDto globalQuote;
}

