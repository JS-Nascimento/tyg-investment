package br.dev.jstec.tyginvestiment.config.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secret.key}")
    private String jwtSecret;


    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(CustomUserDetails userDetails) {
        var now = new Date();
        var duration = getExpiration();
        var validity = new Date(now.getTime() + duration);

        return Jwts.builder()
                .subject(userDetails.getId())
                .claim("preferred_username", ((CustomUserDetails) userDetails).getUsername())
                .issuedAt(now)
                .expiration(validity)
                .signWith(getSecretKey())
                .compact();
    }

    public String generateRefreshToken(CustomUserDetails userDetails) {
        Date now = new Date();
        var duration = getRefreshExpiration();
        Date validity = new Date(now.getTime() + duration);

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(now)
                .expiration(validity)
                .signWith(getSecretKey())
                .compact();
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("preferred_username", String.class);
    }

    public String getSubject(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(authToken);
            return true;
        } catch (JwtException ex) {
            log.error("Invalid JWT signature", ex);
        }
        return false;
    }

    public Long getExpiration() {
        return 60 * 60 * 1000L;
    }

    public Long getRefreshExpiration() {
        return 3600 * 60 * 1000L;
    }
}
