package br.dev.jstec.tyginvestiment.services.handlers;

import br.dev.jstec.tyginvestiment.dto.AssetTransactionDto;
import br.dev.jstec.tyginvestiment.enums.TransactionType;
import br.dev.jstec.tyginvestiment.exception.BusinessException;
import br.dev.jstec.tyginvestiment.repository.AssetTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static br.dev.jstec.tyginvestiment.exception.BusinessErrorMessage.*;
import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
@Slf4j
public class AssetTransactionHandler {

    private final AssetTransactionRepository assetTransactionRepository;

    public AssetTransactionDto create(AssetTransactionDto assetTransactionDto) {
        log.info("Creating asset transaction: {}", assetTransactionDto);

        validateAssetTransaction(assetTransactionDto);

        setDescription(assetTransactionDto);

        return assetTransactionDto;
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
                if (isNull(assetTransactionDto.getQuantity()) || assetTransactionDto.getQuantity() <= 0) {
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
