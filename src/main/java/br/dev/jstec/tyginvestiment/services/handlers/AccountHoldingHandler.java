package br.dev.jstec.tyginvestiment.services.handlers;

import br.dev.jstec.tyginvestiment.dto.AccountHoldingDto;
import br.dev.jstec.tyginvestiment.dto.assetstype.AssetDto;
import br.dev.jstec.tyginvestiment.models.Asset;
import br.dev.jstec.tyginvestiment.repository.AccountHoldingRepository;
import br.dev.jstec.tyginvestiment.services.mappers.AccountHoldingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

import static br.dev.jstec.tyginvestiment.enums.AssetType.getBaseType;

@Component
@RequiredArgsConstructor
public class AccountHoldingHandler {

    private final AccountHoldingRepository repository;
    private final AccountHoldingMapper mapper;
    private final AccountHandler accountHandler;
    private final StockHandler stockHandler;

    private final Map<String, AssetHandler<? extends Asset, ? extends AssetDto>> handlers;

    public AccountHoldingDto save(AccountHoldingDto dto) {
        var account = accountHandler.findById(dto.getAccount().getId());

        if (account == null) {
            throw new RuntimeException("Account not found");
        }

        if(dto.getAsset() == null || dto.getAsset().getAssetType() == null) {
            throw new RuntimeException("Asset not found");
        }

        var assetHandler = handlers.get(getBaseType(dto.getAsset().getAssetType().name()));

        var asset = assetHandler.findById(dto.getAsset().getSymbol());

        dto.setAccount(account);
        dto.setAsset(asset);

        var entity = repository.save(mapper.toEntity(dto));

        return mapper.toDto(entity);
    }
}
