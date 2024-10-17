package br.dev.jstec.tyginvestiment.config.security;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Token {

    private String accessToken;
    private Long expiresIn;
    private String refreshToken;
    private Long refreshExpiresIn;
    private String tokenType;
}
