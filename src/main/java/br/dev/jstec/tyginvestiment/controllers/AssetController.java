package br.dev.jstec.tyginvestiment.controllers;

import br.dev.jstec.tyginvestiment.dto.assetstype.StockDto;
import br.dev.jstec.tyginvestiment.services.handlers.StockHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/assets")
@RequiredArgsConstructor
@Slf4j
public class AssetController {

    private final StockHandler handler;

    @PostMapping("/stocks")
    public ResponseEntity<StockDto> saveAsset(@RequestParam String symbol) {

        var asset = handler.save(symbol.toUpperCase());

        return ResponseEntity.status(201).body(asset);
    }
}
