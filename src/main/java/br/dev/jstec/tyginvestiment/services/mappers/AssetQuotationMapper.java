package br.dev.jstec.tyginvestiment.services.mappers;

import br.dev.jstec.tyginvestiment.clients.dto.GeckoSimplePriceDto;
import br.dev.jstec.tyginvestiment.clients.dto.GeckoSimplePriceDto.CurrencyData;
import br.dev.jstec.tyginvestiment.clients.dto.GlobalQuoteDto;
import br.dev.jstec.tyginvestiment.models.Asset;
import br.dev.jstec.tyginvestiment.models.StockQuotation;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Map;

@Mapper(componentModel = "spring")
public abstract class AssetQuotationMapper {


    @Named("toStockQuotation")
    public StockQuotation toStockQuotation(GlobalQuoteDto globalQuoteDto, Asset asset) {

        if (globalQuoteDto == null || asset == null) {
            return null;
        }

        var quotation = new StockQuotation();
        quotation.setAsset(asset);
        quotation.setOpen(globalQuoteDto.getOpen());
        quotation.setHigh(globalQuoteDto.getHigh());
        quotation.setLow(globalQuoteDto.getLow());
        quotation.setClose(globalQuoteDto.getPrice());
        quotation.setVolume(Double.valueOf(globalQuoteDto.getVolume()));
        quotation.setDate(globalQuoteDto.getLatestTradingDay().atTime(Instant.now().atZone(ZoneId.systemDefault()).toLocalTime()));
        quotation.setPreviousClose(globalQuoteDto.getPreviousClose());
        quotation.setChange(globalQuoteDto.getChange());
        quotation.setChangePercent(globalQuoteDto.getChangePercent());
        return quotation;
    }

    @Named("toCryptoQuotation")
    public StockQuotation toCryptoQuotation(GeckoSimplePriceDto cryptoQuotation, Asset asset) {

        if (cryptoQuotation == null || asset == null) {
            return null;
        }

        for (Map.Entry<String, CurrencyData> entry : cryptoQuotation.getCurrencies().entrySet()) {
            String currencyKey = entry.getKey();
            CurrencyData currencyData = entry.getValue();

            if (currencyKey.equalsIgnoreCase(asset.getName())) {
                var quotation = new StockQuotation();
                quotation.setAsset(asset);

                quotation.setDate(Instant.ofEpochSecond(currencyData.getLastUpdatedAt())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime());

                quotation.setHigh(BigDecimal.valueOf(currencyData.getUsd()));
                quotation.setLow(BigDecimal.valueOf(currencyData.getUsd()));
                quotation.setClose(BigDecimal.valueOf(currencyData.getUsd()));
                quotation.setVolume(currencyData.getUsd24hVol());
                quotation.setMarketCaps(currencyData.getUsdMarketCap());
                quotation.setChange(BigDecimal.valueOf(currencyData.getUsd24hChange()));

                quotation.setOpen(quotation.getClose().subtract(quotation.getChange()));

                var changePercent = quotation.getChange()
                        .divide(quotation.getOpen(), 8, BigDecimal.ROUND_HALF_UP)
                        .multiply(BigDecimal.valueOf(100));

                if (quotation.getClose().compareTo(BigDecimal.ZERO) != 0) {
                    quotation.setChangePercent(changePercent.toString() + "%");
                } else {
                    quotation.setChangePercent("0%");
                }
                return quotation;
            }
        }

        throw new IllegalArgumentException("Dados da moeda não encontrados para o ativo: " + asset.getSymbol());
    }

}
