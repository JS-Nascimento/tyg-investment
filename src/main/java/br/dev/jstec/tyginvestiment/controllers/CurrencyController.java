package br.dev.jstec.tyginvestiment.controllers;

import br.dev.jstec.tyginvestiment.dto.CurrencyDto;
import br.dev.jstec.tyginvestiment.dto.currencies.CurrencyDataDto;
import br.dev.jstec.tyginvestiment.dto.currencies.CurrencyQuotationHistoryDto;
import br.dev.jstec.tyginvestiment.services.handlers.assets.CurrencyHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/currencies")
@Slf4j
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyHandler handler;

    @PostMapping
    public ResponseEntity<CurrencyDto> saveCurrency(@RequestBody CurrencyDto dto) {

        var currency = handler.saveCurrency(dto);

        return ResponseEntity.status(201).body(currency);
    }

    @GetMapping("/{code}")
    public ResponseEntity<CurrencyDto> getCurrency(@PathVariable String code) {
        var currency = handler.getCurrencyDto(code);
        return ResponseEntity.ok(currency);
    }

    @GetMapping("/history")
    public ResponseEntity<List<CurrencyQuotationHistoryDto>> getCurrencyHistory(@RequestParam String code,
                                                                                @RequestParam(defaultValue = "30") int limit) {
        var history = handler.getHistoryByCodeWithLimit(code, limit);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CurrencyDataDto>> getCurrencyList() {

        var list = handler.getExchangeList();
        return ResponseEntity.ok(list);
    }
}
