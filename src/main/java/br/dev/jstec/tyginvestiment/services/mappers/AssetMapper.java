package br.dev.jstec.tyginvestiment.services.mappers;

import br.dev.jstec.tyginvestiment.clients.AlphaVantageClient;
import br.dev.jstec.tyginvestiment.dto.assetstype.StockDto;
import br.dev.jstec.tyginvestiment.enums.AssetType;
import br.dev.jstec.tyginvestiment.models.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public abstract class AssetMapper {

    @Mapping(target = "assetType", source = "assetType", qualifiedByName = "mapAssetType")
    public abstract Stock toEntity(StockDto dto);

    @Mapping(target = "assetType", source = "assetType", qualifiedByName = "mapAssetType")
    public abstract Stock toEntity(AlphaVantageClient dto);

    @Mapping(target = "assetType", source = "assetType", qualifiedByName = "mapAssetType")
    public abstract StockDto toDto(Stock entity);

    @Named("mapAssetType")
    public AssetType mapAssetType(String assetType) {
        return AssetType.valueOf(assetType);
    }

}
