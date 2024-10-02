package br.dev.jstec.tyginvestiment.services.mappers;

import br.dev.jstec.tyginvestiment.clients.AlphaVantageClient;
import br.dev.jstec.tyginvestiment.dto.assetstype.StockDto;
import br.dev.jstec.tyginvestiment.enums.AssetType;
import br.dev.jstec.tyginvestiment.models.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AssetMapper {

    @Mapping(target = "assetType", source = "type", qualifiedByName = "stringToAssetType")
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    Stock toEntity(StockDto dto);

    @Mapping(target = "assetType", source = "assetType", qualifiedByName = "stringToAssetType")
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    Stock toEntity(AlphaVantageClient dto);

    @Mapping(target = "type", source = "assetType")
    StockDto toDto(Stock entity);

    @Named("stringToAssetType")
    default AssetType stringToAssetType(String assetType) {
        return AssetType.valueOf(assetType);
    }
}
