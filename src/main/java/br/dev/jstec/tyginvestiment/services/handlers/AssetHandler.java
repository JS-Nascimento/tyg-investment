package br.dev.jstec.tyginvestiment.services.handlers;

import br.dev.jstec.tyginvestiment.dto.assetstype.AssetDto;
import br.dev.jstec.tyginvestiment.models.Asset;

public interface AssetHandler<T extends Asset, D extends AssetDto> {

    D save(String symbol, String currency);

    D findById(String symbol);

    D save(D dto);

    D save(String symbol);
}
