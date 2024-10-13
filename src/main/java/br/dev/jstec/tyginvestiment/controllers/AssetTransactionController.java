package br.dev.jstec.tyginvestiment.controllers;

import br.dev.jstec.tyginvestiment.dto.AssetTransactionDto;
import br.dev.jstec.tyginvestiment.services.handlers.AssetTransactionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts/transactions")
@RequiredArgsConstructor
@Slf4j
public class AssetTransactionController {

    private final AssetTransactionHandler handler;

    @PostMapping()
    public ResponseEntity<AssetTransactionDto> saveAccount(@RequestBody AssetTransactionDto dto) {
        var transaction = handler.create(dto);
        return ResponseEntity.status(201)
                .body(transaction);
    }
}
