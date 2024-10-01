package br.dev.jstec.tyginvestiment.services.handlers;

import br.dev.jstec.tyginvestiment.dto.AccountHoldingDto;
import br.dev.jstec.tyginvestiment.repository.AccountHoldingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

import static br.dev.jstec.tyginvestiment.enums.AssetType.getBaseType;

@Component
@RequiredArgsConstructor
public class AccountHoldingHandler {

    private final AccountHoldingRepository repository;
    private final AccountHandler accountHandler;
    private final StockHandler stockHandler;
    private final Map<String, AssetHandler> handlers;

    public AccountHoldingDto save(AccountHoldingDto dto) {
        var account = accountHandler.findById(dto.getAccount().getId());

        if (account == null) {
            throw new RuntimeException("Account not found");
        }

        if(dto.getAsset() == null || dto.getAsset().getType() == null) {
            throw new RuntimeException("Asset not found");
        }

        var assetHandler = handlers.get(getBaseType(dto.getAsset().getType().name()));

        var asset = assetHandler.findById(dto.getAsset().getSymbol());

        dto.setAccount(account);
        dto.setAsset(asset);

        var entity = repository.save(dto.toEntity());

        //return entity.toDto();
        return null;
    }
}
