package br.dev.jstec.tyginvestiment.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;
    private UUID tenantId;
    private String name;
    private String email;
    private String password;
}
