package br.dev.jstec.tyginvestiment.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
@ConfigurationProperties(prefix = "alpha-vantage")
@RequiredArgsConstructor
public class ApiKeyManager {

    @Getter
    private List<String> apiKeys;

    private final int limitPerKey = 25;
    private List<AtomicInteger> usageCounters;

    public void setApiKeys(List<String> apiKeys) {
        this.apiKeys = apiKeys;
        this.usageCounters = apiKeys.stream()
                .map(key -> new AtomicInteger(0))
                .collect(Collectors.toList());
    }

    public synchronized String getAvailableApiKey() {
        for (int i = 0; i < apiKeys.size(); i++) {
            if (usageCounters.get(i).get() < limitPerKey) {
                usageCounters.get(i).incrementAndGet();
                return apiKeys.get(i);
            }
        }
        throw new RuntimeException("All API keys have reached their usage limits");
    }

    public synchronized void resetUsageCounters() {
        usageCounters.forEach(counter -> counter.set(0));
    }
}

