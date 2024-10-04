package br.dev.jstec.tyginvestiment.dto.assetstype;

import br.dev.jstec.tyginvestiment.enums.AssetType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = StockDto.class, name = "STOCK")
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AssetDto {

    private String symbol;

    private AssetType assetType;

    private String name;

    private String description;

    private String currency;

}
