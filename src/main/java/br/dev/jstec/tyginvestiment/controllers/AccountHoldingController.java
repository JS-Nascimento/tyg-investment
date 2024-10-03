package br.dev.jstec.tyginvestiment.controllers;

import br.dev.jstec.tyginvestiment.dto.AccountDto;
import br.dev.jstec.tyginvestiment.dto.AccountHoldingDto;
import br.dev.jstec.tyginvestiment.services.handlers.AccountHandler;
import br.dev.jstec.tyginvestiment.services.handlers.AccountHoldingHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts/holdings")
@RequiredArgsConstructor
@Slf4j
public class AccountHoldingController {

    private final AccountHoldingHandler handler;

@PostMapping()
    public ResponseEntity<AccountHoldingDto> saveAccount(@RequestBody AccountHoldingDto dto) {
        var account = handler.save(dto);
        return ResponseEntity.status(201)
                .body(account);
    }
}
