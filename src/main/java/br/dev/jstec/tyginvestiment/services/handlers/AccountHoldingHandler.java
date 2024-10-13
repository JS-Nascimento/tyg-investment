package br.dev.jstec.tyginvestiment.services.handlers;

import br.dev.jstec.tyginvestiment.dto.AccountHoldingDto;
import br.dev.jstec.tyginvestiment.dto.assetstype.AssetDto;
import br.dev.jstec.tyginvestiment.events.AssetTransactionSavedEvent;
import br.dev.jstec.tyginvestiment.exception.BusinessException;
import br.dev.jstec.tyginvestiment.exception.InfrastructureException;
import br.dev.jstec.tyginvestiment.models.Asset;
import br.dev.jstec.tyginvestiment.models.AssetTransaction;
import br.dev.jstec.tyginvestiment.repository.AccountHoldingRepository;
import br.dev.jstec.tyginvestiment.services.mappers.AccountHoldingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;
import java.util.Map;

import static br.dev.jstec.tyginvestiment.enums.AssetType.getBaseType;
import static br.dev.jstec.tyginvestiment.exception.BusinessErrorMessage.ASSET_ALREADY_EXISTS_IN_ACCOUNT;
import static br.dev.jstec.tyginvestiment.exception.ErrorMessage.ACCOUNT_NOT_FOUND;
import static br.dev.jstec.tyginvestiment.exception.ErrorMessage.ASSET_INVALID_INFORMATION;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccountHoldingHandler {

    private final AccountHoldingRepository repository;
    private final AccountHoldingMapper mapper;
    private final AccountHandler accountHandler;
    private final StockHandler stockHandler;
    private final ApplicationEventPublisher publisher;

    private final Map<String, AssetHandler<? extends Asset, ? extends AssetDto>> handlers;

    public AccountHoldingDto findById(Long accountId, String assetId) {
        return repository
                .findAccountHoldingByAccountIdAndAssetId(accountId, assetId)
                .map(mapper::toDto).orElse(null);
    }


    public AccountHoldingDto save(AccountHoldingDto dto) {

        repository.findAccountHoldingByAccountIdAndAssetId(dto.getAccount().getId(), dto.getAsset().getSymbol())
                .ifPresent(holding -> {
                    throw new BusinessException(ASSET_ALREADY_EXISTS_IN_ACCOUNT);
                });

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

        var transaction = mapper.toTransactionByCreateHolding(entity);

        publisher.publishEvent(new AssetTransactionSavedEvent(this, transaction));

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
