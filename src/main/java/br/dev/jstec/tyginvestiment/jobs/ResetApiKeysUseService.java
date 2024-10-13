package br.dev.jstec.tyginvestiment.jobs;

import br.dev.jstec.tyginvestiment.config.ApiKeyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ResetApiKeysUseService {

    private final ApiKeyManager apiKeyManager;

    // @Scheduled(cron = "0 0 0 * * ?")
    public void resetApiKeyUsage() {
        apiKeyManager.resetUsageCounters();
    }
}
