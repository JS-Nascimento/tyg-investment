package br.dev.jstec.tyginvestiment.controllers;

import br.dev.jstec.tyginvestiment.dto.BaseCurrencyDto;
import br.dev.jstec.tyginvestiment.dto.CurrencyDto;
import br.dev.jstec.tyginvestiment.services.handlers.CurrencyHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/currencies")
@Slf4j
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyHandler handler;

    @PostMapping
    public ResponseEntity<BaseCurrencyDto> saveCurrency(@RequestBody CurrencyDto dto) {

        var currency = handler.saveCurrency(dto);

        return ResponseEntity.status(201).body(currency);
    }
}
