package br.dev.jstec.tyginvestiment.dto.assetstype;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AssetAllocationDto {
    private Double domesticEquities;
    private Double foreignEquities;
    private Double bond;
    private Double cash;
    private Double other;
}
