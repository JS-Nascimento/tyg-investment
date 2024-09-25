package br.dev.jstec.tyginvestiment.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Primary
public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {

        return Optional.of(1L);
    }

    @Bean
    @Qualifier("auditorProvider")
    public AuditorAware<Long> auditorProvider() {
        return new AuditorAwareImpl();
    }
}
