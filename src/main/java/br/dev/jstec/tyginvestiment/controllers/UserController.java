package br.dev.jstec.tyginvestiment.controllers;

import br.dev.jstec.tyginvestiment.dto.user.ChangePasswordDto;
import br.dev.jstec.tyginvestiment.dto.user.UserDto;
import br.dev.jstec.tyginvestiment.services.handlers.UserHandler;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static br.dev.jstec.tyginvestiment.config.security.TenantContext.getTenant;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserHandler handler;

    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto dto) {
        var user = handler.saveUser(dto);
        return ResponseEntity.status(201).body(user);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getUserInfo() {
        var user = handler.getUserByTenantId(getTenant());
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> changePassword(@PathVariable
                                               @NotNull(message = "Id do Usuário é obrigatório") UUID id,
                                               @RequestBody @Valid ChangePasswordDto dto) {


        handler.changePassword(id, dto);
        return ResponseEntity.ok().build();
    }
}
