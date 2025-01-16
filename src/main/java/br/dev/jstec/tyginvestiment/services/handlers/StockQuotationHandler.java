package br.dev.jstec.tyginvestiment.services.handlers;

import br.dev.jstec.tyginvestiment.clients.GeckoCoinClient;
import br.dev.jstec.tyginvestiment.clients.alphaclient.AlphaClient;
import br.dev.jstec.tyginvestiment.dto.assetstype.brapi.HistoricalDataPriceDTO;
import br.dev.jstec.tyginvestiment.enums.AssetType;
import br.dev.jstec.tyginvestiment.exception.BusinessException;
import br.dev.jstec.tyginvestiment.models.Asset;
import br.dev.jstec.tyginvestiment.repository.AssetRepository;
import br.dev.jstec.tyginvestiment.repository.StockQuotationRepository;
import br.dev.jstec.tyginvestiment.services.mappers.AssetQuotationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static br.dev.jstec.tyginvestiment.exception.BusinessErrorMessage.ASSET_NOT_FOUND;
import static br.dev.jstec.tyginvestiment.exception.BusinessErrorMessage.HISTORY_NOT_FOUND;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockQuotationHandler {

    private final StockQuotationRepository stockQuotationRepository;
    private final AssetRepository assetRepository;
    private final AlphaClient alphaClient;
    private final GeckoCoinClient geckoClient;
    private final AssetQuotationMapper assetQuotationMapper;

    @Value("${alpha-vantage.api-key}")
    private String apiKey;

    @Transactional
    public void updateQuotations() {

        Set<Asset> assets = assetRepository.findDistinctSymbols();

        assets.forEach(asset -> {
            if (AssetType.CRYPTO.equals(asset.getAssetType())) {
                log.info("Atualizando cotação da criptomoeda {}", asset.getSymbol());

                var cryptoQuotation = geckoClient.getCryptoSimplePrice(asset.getName().toLowerCase(), asset.getCurrency());

                if (cryptoQuotation != null) {
                    stockQuotationRepository.saveAndFlush(assetQuotationMapper.toCryptoQuotation(cryptoQuotation, asset));
                    log.info("Cotação da criptomoeda {} atualizada com sucesso", asset.getSymbol());
                }
            } else {

                log.info("Atualizando cotação da ação {}", asset.getSymbol());

                var stockQuotation = alphaClient.getGlobalQuote(asset.getSymbol(), apiKey);
                if (stockQuotation != null) {
                    stockQuotationRepository.saveAndFlush(assetQuotationMapper.toStockQuotation(stockQuotation.getGlobalQuote(), asset));
                    log.info("Cotação da ação {} atualizada com sucesso", asset.getSymbol());
                }
            }
        });
    }

    @Transactional
    public void saveHistoricalData(String symbol, List<HistoricalDataPriceDTO> historicalData) {
        log.info("Getting historical data for {}", symbol);

        var asset = assetRepository.findBySymbol(symbol)
                .orElseThrow(() -> new BusinessException(ASSET_NOT_FOUND));

        if (historicalData.isEmpty()) {
            throw new BusinessException(HISTORY_NOT_FOUND, symbol);
        }

        stockQuotationRepository.saveAllAndFlush(assetQuotationMapper.toBrapiQuotationList(historicalData, asset));
    }
}