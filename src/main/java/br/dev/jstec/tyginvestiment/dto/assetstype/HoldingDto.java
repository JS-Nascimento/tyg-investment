package br.dev.jstec.tyginvestiment.dto.assetstype;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HoldingDto {
    private String symbol;
    private String description;
    private Double weight;
}
