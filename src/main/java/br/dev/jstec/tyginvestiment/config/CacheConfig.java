package br.dev.jstec.tyginvestiment.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.concurrent.TimeUnit.*;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder().expireAfterWrite(60, MILLISECONDS);
    }

    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        var cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(
                Caffeine.newBuilder()
                        .initialCapacity(100)
                        .maximumSize(500)
                        .expireAfterAccess(10, MINUTES));

        cacheManager.registerCustomCache("exchange-list",
                Caffeine.newBuilder()
                        .expireAfterAccess(1, DAYS)
                        .build());
        return cacheManager;
    }
}
