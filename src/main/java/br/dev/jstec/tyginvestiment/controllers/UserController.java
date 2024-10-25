package br.dev.jstec.tyginvestiment.controllers;

import br.dev.jstec.tyginvestiment.dto.UserDto;
import br.dev.jstec.tyginvestiment.services.handlers.UserHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static br.dev.jstec.tyginvestiment.config.security.TenantContext.getTenant;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserHandler handler;

    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto dto) {
        var user = handler.saveUser(dto);
        return ResponseEntity.status(201).body(user);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getUser(@PathVariable UUID id) {
        var user = handler.getUserByTenantId(getTenant());
        return ResponseEntity.ok(user);
    }
}
