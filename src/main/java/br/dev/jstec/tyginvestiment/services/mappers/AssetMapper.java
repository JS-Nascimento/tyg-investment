package br.dev.jstec.tyginvestiment.services.mappers;

import br.dev.jstec.tyginvestiment.clients.dto.AlphaVantageClient;
import br.dev.jstec.tyginvestiment.clients.dto.CoinGeckoCriptoDto;
import br.dev.jstec.tyginvestiment.clients.dto.EtfProfileDto;
import br.dev.jstec.tyginvestiment.dto.assetstype.CryptoDto;
import br.dev.jstec.tyginvestiment.dto.assetstype.FundDto;
import br.dev.jstec.tyginvestiment.dto.assetstype.StockDto;
import br.dev.jstec.tyginvestiment.enums.AssetType;
import br.dev.jstec.tyginvestiment.models.Crypto;
import br.dev.jstec.tyginvestiment.models.Fund;
import br.dev.jstec.tyginvestiment.models.Stock;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public abstract class AssetMapper {

    @Mapping(target = "assetType", source = "assetType", qualifiedByName = "mapAssetType")
    public abstract Stock toEntity(StockDto dto);

    @Mapping(target = "assetType", source = "assetType", qualifiedByName = "mapAssetType")
    public abstract Crypto toEntity(CryptoDto dto);

    @Mapping(target = "assetType", source = "assetType", qualifiedByName = "mapAssetType")
    public abstract Fund toEntity(FundDto dto);

    @Mapping(target = "assetType", source = "assetType", qualifiedByName = "mapAssetType")
    public abstract Crypto toEntity(CoinGeckoCriptoDto dto);

    @Mapping(target = "assetType", source = "assetType", qualifiedByName = "mapAssetType")
    @Mapping(target = "dividendDate", source = "dividendDate", qualifiedByName = "mapDateOrNull")
    @Mapping(target = "exDividendDate", source = "exDividendDate", qualifiedByName = "mapDateOrNull")
    public abstract Stock toEntity(AlphaVantageClient dto);

    @Mapping(target = "assetType", source = "assetType", qualifiedByName = "mapAssetType")
    public abstract Fund toEntity(EtfProfileDto dto);

    @Mapping(target = "assetType", source = "assetType", qualifiedByName = "mapAssetType")
    public abstract StockDto toDto(Stock entity);

    @Mapping(target = "assetType", source = "assetType", qualifiedByName = "mapAssetType")
    public abstract FundDto toDto(Fund entity);

    @Mapping(target = "assetType", source = "assetType", qualifiedByName = "mapAssetType")
    public abstract CryptoDto toDto(Crypto entity);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "symbol", source = "symbol")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "currency", source = "currency")
    @Mapping(target = "country", source = "country")
    @Mapping(target = "assetType", source = "assetType", qualifiedByName = "mapAssetType")
    public abstract StockDto toDtoSimplified(Stock entity);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "symbol", source = "symbol")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "currency", source = "currency")
    @Mapping(target = "assetType", source = "assetType", qualifiedByName = "mapAssetType")
    public abstract FundDto toDtoSimplified(Fund entity);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "symbol", source = "symbol")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "currency", source = "currency")
    @Mapping(target = "assetType", source = "assetType", qualifiedByName = "mapAssetType")
    public abstract CryptoDto toDtoSimplified(Crypto entity);

    @Named("mapAssetType")
    public AssetType mapAssetType(String assetType) {
        return AssetType.isStock(assetType);
    }

    @Named("mapDateOrNull")
    public LocalDate mapDateOrNull(String date) {
        try {
            return LocalDate.parse(date);
        } catch (Exception e) {
            return null;
        }
    }
}
