package br.dev.jstec.tyginvestiment.controllers;

import br.dev.jstec.tyginvestiment.dto.assetstype.AssetDto;
import br.dev.jstec.tyginvestiment.enums.AssetMarketLocation;
import br.dev.jstec.tyginvestiment.services.handlers.assets.CryptoHandler;
import br.dev.jstec.tyginvestiment.services.handlers.assets.FundHandler;
import br.dev.jstec.tyginvestiment.services.handlers.assets.StockHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/assets")
@RequiredArgsConstructor
@Slf4j
public class AssetController {

    private final StockHandler stockHandler;
    private final FundHandler fundHandler;
    private final CryptoHandler cryptoHandler;

    @PostMapping("/stocks")
    public ResponseEntity<AssetDto> saveAsset(
            @RequestParam AssetMarketLocation marketLocation,
            @RequestParam String symbol) {

        var asset = stockHandler.save(marketLocation, List.of(symbol.toUpperCase()));

        return ResponseEntity.status(201).body(asset);
    }

    @PostMapping("/funds")
    public ResponseEntity<AssetDto> saveAsset(@RequestParam String symbol, @RequestParam AssetMarketLocation marketLocation) {

        var asset = fundHandler.save(marketLocation, symbol.toUpperCase());

        return ResponseEntity.status(201).body(asset);
    }

    @PostMapping("/cryptos")
    public ResponseEntity<AssetDto> saveCrypto(@RequestParam String symbol, @RequestParam AssetMarketLocation marketLocation) {

        var asset = cryptoHandler.save(marketLocation, symbol.toUpperCase());

        return ResponseEntity.status(201).body(asset);
    }
}
