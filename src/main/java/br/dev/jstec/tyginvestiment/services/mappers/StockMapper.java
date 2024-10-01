package br.dev.jstec.tyginvestiment.services.mappers;

import br.dev.jstec.tyginvestiment.clients.AlphaVantageClient;
import br.dev.jstec.tyginvestiment.dto.assetstype.StockDto;
import br.dev.jstec.tyginvestiment.models.Stock;

import static br.dev.jstec.tyginvestiment.enums.AssetType.isStock;
import static br.dev.jstec.tyginvestiment.utils.DateUtils.DateUtil.parseDate;

public class StockMapper {

    public static Stock toEntity(AlphaVantageClient dto) {

        var entity = new Stock();

        entity.setSymbol(dto.getSymbol());
        entity.setAssetType(isStock(dto.getAssetType()));
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setCik(dto.getCik());
        entity.setExchange(dto.getExchange());
        entity.setCurrency(dto.getCurrency());
        entity.setCountry(dto.getCountry());
        entity.setSector(dto.getSector());
        entity.setIndustry(dto.getIndustry());
        entity.setOfficialSite(dto.getOfficialSite());
        entity.setMarketCapitalization(dto.getMarketCapitalization());
        entity.setEbitda(dto.getEbitda());
        entity.setPeRatio(dto.getPeRatio());
        entity.setPegRatio(dto.getPegRatio());
        entity.setBookValue(dto.getBookValue());
        entity.setDividendPerShare(dto.getDividendPerShare());
        entity.setDividendYield(dto.getDividendYield());
        entity.setEps(dto.getEps());
        entity.setRevenuePerShareTTM(dto.getRevenuePerShareTTM());
        entity.setProfitMargin(dto.getProfitMargin());
        entity.setOperatingMarginTTM(dto.getOperatingMarginTTM());
        entity.setReturnOnAssetsTTM(dto.getReturnOnAssetsTTM());
        entity.setReturnOnEquityTTM(dto.getReturnOnEquityTTM());
        entity.setRevenueTTM(dto.getRevenueTTM());
        entity.setGrossProfitTTM(dto.getGrossProfitTTM());
        entity.setAnalystTargetPrice(dto.getAnalystTargetPrice());
        entity.setPriceToSalesRatioTTM(dto.getPriceToSalesRatioTTM());
        entity.setPriceToBookRatio(dto.getPriceToBookRatio());
        entity.setFiftyTwoWeekHigh(dto.getFiftyTwoWeekHigh());
        entity.setFiftyTwoWeekLow(dto.getFiftyTwoWeekLow());
        entity.setFiftyDayMovingAverage(dto.getFiftyDayMovingAverage());
        entity.setTwoHundredDayMovingAverage(dto.getTwoHundredDayMovingAverage());
        entity.setSharesOutstanding(dto.getSharesOutstanding());
        entity.setDividendDate(parseDate(dto.getDividendDate()));
        entity.setExDividendDate(parseDate(dto.getExDividendDate()));

        return entity;
    }

    public static StockDto toDto(Stock entity) {

        if (entity == null) {
            return null;
        }

        var dto = new StockDto();

        dto.setSymbol(entity.getSymbol());
        dto.setType(entity.getAssetType());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setCik(entity.getCik());
        dto.setExchange(entity.getExchange());
        dto.setCurrency(entity.getCurrency());
        dto.setCountry(entity.getCountry());
        dto.setSector(entity.getSector());
        dto.setIndustry(entity.getIndustry());
        dto.setOfficialSite(entity.getOfficialSite());
        dto.setMarketCapitalization(entity.getMarketCapitalization());
        dto.setEbitda(entity.getEbitda());
        dto.setPeRatio(entity.getPeRatio());
        dto.setPegRatio(entity.getPegRatio());
        dto.setBookValue(entity.getBookValue());
        dto.setDividendPerShare(entity.getDividendPerShare());
        dto.setDividendYield(entity.getDividendYield());
        dto.setEps(entity.getEps());
        dto.setRevenuePerShareTTM(entity.getRevenuePerShareTTM());
        dto.setProfitMargin(entity.getProfitMargin());
        dto.setOperatingMarginTTM(entity.getOperatingMarginTTM());
        dto.setReturnOnAssetsTTM(entity.getReturnOnAssetsTTM());
        dto.setReturnOnEquityTTM(entity.getReturnOnEquityTTM());
        dto.setRevenueTTM(entity.getRevenueTTM());
        dto.setGrossProfitTTM(entity.getGrossProfitTTM());
        dto.setAnalystTargetPrice(entity.getAnalystTargetPrice());
        dto.setPriceToSalesRatioTTM(entity.getPriceToSalesRatioTTM());
        dto.setPriceToBookRatio(entity.getPriceToBookRatio());
        dto.setFiftyTwoWeekHigh(entity.getFiftyTwoWeekHigh());
        dto.setFiftyTwoWeekLow(entity.getFiftyTwoWeekLow());
        dto.setFiftyDayMovingAverage(entity.getFiftyDayMovingAverage());
        dto.setTwoHundredDayMovingAverage(entity.getTwoHundredDayMovingAverage());
        dto.setSharesOutstanding(entity.getSharesOutstanding());
        dto.setDividendDate(entity.getDividendDate());
        dto.setExDividendDate(entity.getExDividendDate());

        return dto;
    }
}
