package br.dev.jstec.tyginvestiment.services.handlers;

import br.dev.jstec.tyginvestiment.dto.AccountHoldingDto;
import br.dev.jstec.tyginvestiment.dto.assetstype.AssetDto;
import br.dev.jstec.tyginvestiment.exception.InfrastructureException;
import br.dev.jstec.tyginvestiment.models.Asset;
import br.dev.jstec.tyginvestiment.models.AssetTransaction;
import br.dev.jstec.tyginvestiment.repository.AccountHoldingRepository;
import br.dev.jstec.tyginvestiment.services.mappers.AccountHoldingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;
import java.util.Map;

import static br.dev.jstec.tyginvestiment.enums.AssetType.getBaseType;
import static br.dev.jstec.tyginvestiment.exception.ErrorMessage.ACCOUNT_NOT_FOUND;
import static br.dev.jstec.tyginvestiment.exception.ErrorMessage.ASSET_INVALID_INFORMATION;

@Component
@RequiredArgsConstructor
public class AccountHoldingHandler {

    private final AccountHoldingRepository repository;
    private final AccountHoldingMapper mapper;
    private final AccountHandler accountHandler;
    private final StockHandler stockHandler;
    private final AssetTransactionHandler assetTransactionHandler;

    private final Map<String, AssetHandler<? extends Asset, ? extends AssetDto>> handlers;

    public AccountHoldingDto save(AccountHoldingDto dto) {
        var account = accountHandler.findById(dto.getAccount().getId());

        if (account == null) {
            throw new InfrastructureException(ACCOUNT_NOT_FOUND, String.valueOf(dto.getAccount().getId()));
        }

        if(dto.getAsset() == null || dto.getAsset().getAssetType() == null) {
            throw new InfrastructureException(ASSET_INVALID_INFORMATION);
        }

        var assetHandler = handlers.get(getBaseType(dto.getAsset().getAssetType().name()));

        var asset = assetHandler.findById(dto.getAsset().getSymbol());

        dto.setAccount(account);
        dto.setAsset(asset);

        var entity = repository.save(mapper.toEntity(dto));

        assetTransactionHandler.create(mapper.toTransactionByCreateHolding(entity));

        return mapper.toDto(entity);
    }


    public void updateHoldingAfterTransaction(AssetTransaction transaction) {
        var assetHolding = repository
                .findAccountHoldingByAccountIdAndAssetId(
                        transaction.getAccount().getId(),
                        transaction.getAsset().getSymbol())
                .orElseThrow(() -> new InfrastructureException(ASSET_INVALID_INFORMATION));

        switch (transaction.getTransactionType()) {
            case BUY:
                assetHolding.setQuantity(assetHolding.getQuantity().add(transaction.getQuantity()));
                assetHolding.setAveragePrice(
                        assetHolding.getAveragePrice()
                                .multiply(assetHolding.getQuantity())
                                .add(transaction.getValue().multiply(transaction.getQuantity()))
                                .divide(assetHolding.getQuantity().add(transaction.getQuantity()))
                                .setScale(4, RoundingMode.HALF_EVEN));
                break;
            case SELL:
                assetHolding.setQuantity(assetHolding.getQuantity().subtract(transaction.getQuantity()));
                break;
            case DIVIDEND:
                assetHolding.setDividendAmount(assetHolding.getDividendAmount().add(transaction.getValue()));
                break;
        }

        repository.save(assetHolding);
    }
}
