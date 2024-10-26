package br.dev.jstec.tyginvestiment.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ChangePasswordDto {

    private UUID tenantId;

    @NotBlank(message = "A Senha Atual é obrigatória")
    private String oldPassword;

    @NotBlank(message = "A Nova Senha é obrigatória")
    private String newPassword;

    @NotBlank(message = "A Confirmação da Nova Senha é obrigatória")
    private String confirmPassword;

}
