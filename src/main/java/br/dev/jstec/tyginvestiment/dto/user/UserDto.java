package br.dev.jstec.tyginvestiment.dto.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private String baseCurrency;

    @JsonIgnore
    private String password;
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean isAccountNonLocked;

    @JsonProperty("settings")
    private UserSettingsDto userSettings;
}
