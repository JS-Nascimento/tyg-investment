package br.dev.jstec.tyginvestiment.config.security;

import br.dev.jstec.tyginvestiment.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;

    public JWTLoginFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        setFilterProcessesUrl("/auth");
    }

    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            User credentials =
                    new ObjectMapper().readValue(request.getInputStream(), User.class);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword(), new ArrayList<>());

            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain,
                                            Authentication auth) throws IOException {
        CustomUserDetails principal = (CustomUserDetails) auth.getPrincipal();

        String accessToken = jwtTokenProvider.generateToken(principal);
        String refreshToken = jwtTokenProvider.generateRefreshToken(principal);

        var token = Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtTokenProvider.getExpiration())
                .refreshExpiresIn(jwtTokenProvider.getRefreshExpiration())
                .build();

        response.addHeader("Authorization", "Bearer " + accessToken);
        response.addHeader("Refresh", "Bearer " + refreshToken);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        ObjectMapper objectMapper = new ObjectMapper();
        String tokenJson = objectMapper.writeValueAsString(token);

        response.getWriter().write(tokenJson);
    }
}
