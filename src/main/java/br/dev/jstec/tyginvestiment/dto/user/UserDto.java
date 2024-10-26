package br.dev.jstec.tyginvestiment.dto.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UserDto {

    @JsonIgnore
    private Long id;
    private UUID tenantId;
    private String name;
    private String email;

    @JsonIgnore
    private String password;
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean isAccountNonLocked;
}
