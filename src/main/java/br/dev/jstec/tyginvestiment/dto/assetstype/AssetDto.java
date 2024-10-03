package br.dev.jstec.tyginvestiment.dto.assetstype;

import br.dev.jstec.tyginvestiment.enums.AssetType;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class AssetDto {

    private String symbol;

    private AssetType assetType;

    private String name;

    private String description;

    private String currency;

}
