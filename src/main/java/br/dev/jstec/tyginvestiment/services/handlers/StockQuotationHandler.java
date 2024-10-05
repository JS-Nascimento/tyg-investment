package br.dev.jstec.tyginvestiment.services.handlers;

import br.dev.jstec.tyginvestiment.clients.AlphaClient;
import br.dev.jstec.tyginvestiment.config.ApiKeyManager;
import br.dev.jstec.tyginvestiment.models.Asset;
import br.dev.jstec.tyginvestiment.repository.AssetRepository;
import br.dev.jstec.tyginvestiment.repository.StockQuotationRepository;
import br.dev.jstec.tyginvestiment.services.mappers.AssetQuotationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockQuotationHandler {

    private final StockQuotationRepository stockQuotationRepository;
    private final AssetRepository assetRepository;
    private final AlphaClient alphaClient;
    private final AssetQuotationMapper mapper;
    private final ApiKeyManager apiKeyManager;

    @Transactional
    public void updateQuotations() {

        Set<Asset> assets = assetRepository.findDistinctSymbols();

        assets.forEach(asset -> {
            log.info("Atualizando cotação da ação {}", asset.getSymbol());

            var stockQuotation = alphaClient.getGlobalQuote(asset.getSymbol(), apiKeyManager.getAvailableApiKey());

            if (stockQuotation != null) {
                stockQuotationRepository.saveAndFlush(mapper.toStockQuotation(stockQuotation.getGlobalQuote(), asset));
                log.info("Cotação da ação {} atualizada com sucesso", asset.getSymbol());
            }
            ;
        });
    }
}