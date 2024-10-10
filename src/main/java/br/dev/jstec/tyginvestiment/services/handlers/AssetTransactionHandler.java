package br.dev.jstec.tyginvestiment.services.handlers;

import br.dev.jstec.tyginvestiment.dto.AssetTransactionDto;
import br.dev.jstec.tyginvestiment.enums.TransactionType;
import br.dev.jstec.tyginvestiment.events.AssetTransactionSavedEvent;
import br.dev.jstec.tyginvestiment.exception.BusinessException;
import br.dev.jstec.tyginvestiment.exception.InfrastructureException;
import br.dev.jstec.tyginvestiment.repository.AccountRepository;
import br.dev.jstec.tyginvestiment.repository.AssetRepository;
import br.dev.jstec.tyginvestiment.repository.AssetTransactionRepository;
import br.dev.jstec.tyginvestiment.services.mappers.AssetTransactionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static br.dev.jstec.tyginvestiment.exception.BusinessErrorMessage.*;
import static br.dev.jstec.tyginvestiment.exception.ErrorMessage.ACCOUNT_NOT_FOUND;
import static br.dev.jstec.tyginvestiment.exception.ErrorMessage.ASSET_NOT_FOUND;
import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
@Slf4j
public class AssetTransactionHandler {

    private final AssetTransactionRepository repository;
    private final AssetRepository assetRepository;
    private final AccountRepository accountRepository;
    private final AssetTransactionMapper mapper;
    private final ApplicationEventPublisher publisher;

    public AssetTransactionDto create(AssetTransactionDto assetTransactionDto) {
        log.info("Creating asset transaction: {}", assetTransactionDto);

        validateAssetTransaction(assetTransactionDto);
        setDescription(assetTransactionDto);

        var asset = assetRepository.findBySymbol(assetTransactionDto.getAssetId())
                .orElseThrow(() -> new InfrastructureException(ASSET_NOT_FOUND));

        var account = accountRepository.findById(assetTransactionDto.getAccountId())
                .orElseThrow(() -> new InfrastructureException(ACCOUNT_NOT_FOUND));

        if (assetTransactionDto.getTransactionType().equals(TransactionType.BUY.name())) {
            var accountBalance = account.getAvailableBalance();
            var transactionValue = assetTransactionDto.getValue().multiply(assetTransactionDto.getQuantity());
            if (accountBalance.compareTo(transactionValue) < 0) {
                throw new BusinessException(INSUFFICIENT_FUNDS);
            }
        }

        var entity = mapper.toEntity(assetTransactionDto, asset, account);

        var transaction = repository.save(entity);

        publisher.publishEvent(new AssetTransactionSavedEvent(this, transaction));

        return mapper.toDto(transaction);
    }

    private void validateAssetTransaction(AssetTransactionDto assetTransactionDto) {
        log.info("Validating asset transaction: {}", assetTransactionDto);

        if (isNull(assetTransactionDto.getTransactionType())) {
            throw new BusinessException(TRANSACTION_TYPE_REQUIRED);
        }

        if (isNull(assetTransactionDto.getAssetId())) {
            throw new BusinessException(ASSET_ID_REQUIRED);
        }

        if (isNull(assetTransactionDto.getAccountId())) {
            throw new BusinessException(ACCOUNT_ID_REQUIRED);
        }

        switch (TransactionType.valueOf(assetTransactionDto.getTransactionType())) {
            case BUY:
            case SELL:
                if (isNull(assetTransactionDto.getQuantity()) || assetTransactionDto.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
                    throw new BusinessException(QUANTITY_REQUIRED);
                }
                if (isNull(assetTransactionDto.getValue()) || assetTransactionDto.getValue().compareTo(BigDecimal.ZERO) <= 0) {
                    throw new BusinessException(VALUE_REQUIRED);
                }
                break;
            case DIVIDEND:
                if (isNull(assetTransactionDto.getValue()) || assetTransactionDto.getValue().compareTo(BigDecimal.ZERO) <= 0) {
                    throw new BusinessException(VALUE_REQUIRED);
                }
            case OTHER:
                break;
        }
    }

    private void setDescription(AssetTransactionDto assetTransactionDto) {
        switch (TransactionType.valueOf(assetTransactionDto.getTransactionType())) {
            case BUY ->
                    assetTransactionDto.setDescription("Compra de " + assetTransactionDto.getQuantity() + " cotas de " + assetTransactionDto.getAssetId());
            case SELL ->
                    assetTransactionDto.setDescription("Venda de " + assetTransactionDto.getQuantity() + " cotas de " + assetTransactionDto.getAssetId());
            case DIVIDEND ->
                    assetTransactionDto.setDescription("Dividendo de " + assetTransactionDto.getValue() + " recebido de " + assetTransactionDto.getAssetId());
            case OTHER -> assetTransactionDto.setDescription("Outros");
        }
        ;
    }
}
