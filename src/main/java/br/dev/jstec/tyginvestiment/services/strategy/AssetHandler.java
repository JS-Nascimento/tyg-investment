package br.dev.jstec.tyginvestiment.services.strategy;

import br.dev.jstec.tyginvestiment.dto.assetstype.AssetDto;
import br.dev.jstec.tyginvestiment.enums.AssetMarketLocation;
import br.dev.jstec.tyginvestiment.enums.AssetType;
import br.dev.jstec.tyginvestiment.exception.BusinessException;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import static br.dev.jstec.tyginvestiment.exception.BusinessErrorMessage.*;
import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class AssetHandler {

    private final Map<String, AssetStrategy> assetStrategies;

    public <T extends AssetDto> T save(AssetMarketLocation assetMarketLocation, AssetType assetType, String symbol) {

        validateInputs(assetMarketLocation, assetType, symbol);

        var assetStrategyHandler = assetStrategies.get(assetType.name());
        if (isNull(assetStrategyHandler)) {
            throw new BusinessException(ASSET_NOT_FOUND);
        }

        return assetStrategyHandler.save(assetMarketLocation, symbol);
    }

    private void validateInputs(AssetMarketLocation assetMarketLocation, AssetType assetType, String symbol) {
        if (isNull(assetType)) {
            throw new BusinessException(ASSET_TYPE_REQUIRED);
        }
        if (isNull(assetMarketLocation)) {
            throw new BusinessException(ASSET_MARKET_LOCATION_REQUIRED);
        }
        if (isNull(symbol)) {
            throw new BusinessException(ASSET_SYMBOL_REQUIRED);
        }
    }
}
