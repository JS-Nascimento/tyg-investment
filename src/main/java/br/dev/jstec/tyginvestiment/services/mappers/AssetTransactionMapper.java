package br.dev.jstec.tyginvestiment.services.mappers;

import br.dev.jstec.tyginvestiment.dto.AssetTransactionDto;
import br.dev.jstec.tyginvestiment.models.Account;
import br.dev.jstec.tyginvestiment.models.Asset;
import br.dev.jstec.tyginvestiment.models.AssetTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import static br.dev.jstec.tyginvestiment.enums.TransactionType.fromString;

@Mapper(componentModel = "spring")
public abstract class AssetTransactionMapper {

    @Named("toEntity")
    public AssetTransaction toEntity(AssetTransactionDto dto, Asset asset, Account account) {
        if (dto == null) {
            return null;
        }

        AssetTransaction entity = new AssetTransaction();
        entity.setId(dto.getId());
        entity.setDescription(dto.getDescription());
        entity.setTransactionType(fromString(dto.getTransactionType()));
        entity.setTransactionDate(dto.getTransactionDate());
        entity.setQuantity(dto.getQuantity());
        entity.setValue(dto.getValue());
        entity.setAsset(asset);
        entity.setAccount(account);
        return entity;
    }

    @Named("toDto")
    public AssetTransactionDto toDto(AssetTransaction entity) {
        if (entity == null) {
            return null;
        }

        AssetTransactionDto dto = new AssetTransactionDto();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setTransactionType(entity.getTransactionType().name());
        dto.setTransactionDate(entity.getTransactionDate());
        dto.setQuantity(entity.getQuantity());
        dto.setValue(entity.getValue());
        dto.setAssetId(entity.getAsset().getSymbol());
        dto.setAccountId(entity.getAccount().getId());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedAt(entity.getCreatedDate());
        return dto;
    }
}
