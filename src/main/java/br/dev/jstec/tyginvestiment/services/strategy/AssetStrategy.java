package br.dev.jstec.tyginvestiment.services.strategy;

import br.dev.jstec.tyginvestiment.dto.assetstype.AssetDto;
import br.dev.jstec.tyginvestiment.enums.AssetMarketLocation;

public interface AssetStrategy {

    <T extends AssetDto> T save(AssetMarketLocation marketLocation, String symbol);

    <T extends AssetDto> T findById(String symbol);
}
