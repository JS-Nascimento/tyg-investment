package br.dev.jstec.tyginvestiment.dto.assetstype;

import br.dev.jstec.tyginvestiment.enums.AssetType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class AssetDto {

    private String symbol;

    private AssetType type;

    private String name;

    private String description;

    private String currency;

}
