package br.dev.jstec.tyginvestiment.clients;

import br.dev.jstec.tyginvestiment.dto.CurrencyLayerResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class CurrencyLayerClient {
    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String baseUrl;
    private final String baseCurrency;

    public CurrencyLayerClient(RestTemplate restTemplate,
                                @Value("${currency-layer.api-key}") String apiKey,
                                @Value("${currency-layer.base-url}") String baseUrl,
                                @Value("${app.config.currency-base}") String baseCurrency) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.baseCurrency = baseCurrency;
    }

    public CurrencyLayerResponse getLiveRates() {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/live")
                .queryParam("access_key", apiKey)
                .queryParam("source", baseCurrency)
                .toUriString();

        return restTemplate.getForObject(url, CurrencyLayerResponse.class);
    }

}
