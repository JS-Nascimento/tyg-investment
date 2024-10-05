package br.dev.jstec.tyginvestiment.controllers;

import br.dev.jstec.tyginvestiment.dto.AccountDto;
import br.dev.jstec.tyginvestiment.dto.accountsummary.InvestmentSummaryStatementDto;
import br.dev.jstec.tyginvestiment.services.handlers.AccountHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountHandler handler;

    @PostMapping()
    public ResponseEntity<AccountDto> saveAccount(@RequestBody AccountDto dto) {
        var account = handler.saveAccount(dto);
        return ResponseEntity.status(201).body(account);
    }

    @GetMapping("/{id}/summary")
    public ResponseEntity<InvestmentSummaryStatementDto> getAccountSummary(@PathVariable Long id) {
        var account = handler.getAccountSummary(id);
        return ResponseEntity.ok(account);
    }
}
