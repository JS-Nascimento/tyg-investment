package br.dev.jstec.tyginvestiment.config.security;

import br.dev.jstec.tyginvestiment.exception.RequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static br.dev.jstec.tyginvestiment.exception.ErrorMessage.INVALID_TOKEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Component
@Slf4j
public class TenantContext {

    private static CustomUserDetails getUserLoggedByToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken userDetails) {
            return (CustomUserDetails) userDetails.getPrincipal();
        }
        throw new RequestException(UNAUTHORIZED, INVALID_TOKEN, TenantContext.class.getName());
    }

    public static UUID getTenant() {
        var user = getUserLoggedByToken();
        return UUID.fromString(user.getId());
    }

    public static String getTenantUsername() {
        var user = getUserLoggedByToken();
        return user.getUsername();
    }

    public static String getTenantBaseCurrency() {
        var user = getUserLoggedByToken();
        return user.getBaseCurrency();
    }
}
