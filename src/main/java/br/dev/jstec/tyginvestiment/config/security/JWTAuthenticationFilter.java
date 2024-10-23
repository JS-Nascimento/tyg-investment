package br.dev.jstec.tyginvestiment.config.security;

import br.dev.jstec.tyginvestiment.exception.ResponseErrorMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static br.dev.jstec.tyginvestiment.exception.ErrorMessage.USER_EMAIL_NOT_VERIFIED;


@Slf4j
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
                                   JwtTokenProvider jwtTokenProvider,
                                   CustomUserDetailsService userDetailsService) {
        super(authenticationManager);
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String jwtToken = token.substring(7);
        String username = jwtTokenProvider.getUsernameFromJWT(jwtToken);
        String subject = jwtTokenProvider.getSubject(jwtToken);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            CustomUserDetails userDetails = userDetailsService.loadUserByUsername(username, subject);
            if (!userDetails.isEmailVerified()) {
                var mapper = getObjectMapper();
                var timeStamp = LocalDateTime.now();
                var forbiddenError = ResponseErrorMessage.builder()
                        .errorCode(USER_EMAIL_NOT_VERIFIED.getCode())
                        .errorMessage(USER_EMAIL_NOT_VERIFIED.getMessage())
                        .errorTimestamp(timeStamp.atZone(ZoneId.systemDefault()).toInstant())
                        .build();

                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write(mapper.writeValueAsString(
                        forbiddenError));
                return;
            }

            if (jwtTokenProvider.validateToken(jwtToken)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        chain.doFilter(request, response);
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper;
    }
}
