package br.dev.jstec.tyginvestiment.services.strategy;

import br.dev.jstec.tyginvestiment.dto.assetstype.AssetDto;
import br.dev.jstec.tyginvestiment.enums.AssetMarketLocation;

import java.util.List;

public interface AssetStrategy {

    AssetDto save(AssetMarketLocation marketLocation, String symbol);

    AssetDto save(AssetMarketLocation marketLocation, List<String> symbols);

    AssetDto findById(String symbol);
}
