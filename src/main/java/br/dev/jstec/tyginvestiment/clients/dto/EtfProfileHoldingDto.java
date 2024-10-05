package br.dev.jstec.tyginvestiment.clients.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EtfProfileHoldingDto {
    private String symbol;
    private String description;
    private Double weight;
}
