package br.dev.jstec.tyginvestiment.services.mappers;

import br.dev.jstec.tyginvestiment.clients.dto.GlobalQuoteDto;
import br.dev.jstec.tyginvestiment.models.Asset;
import br.dev.jstec.tyginvestiment.models.StockQuotation;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public abstract class AssetQuotationMapper {


    @Named("toStockQuotation")
    public StockQuotation toStockQuotation(GlobalQuoteDto globalQuoteDto, Asset asset) {

        if (globalQuoteDto == null || asset == null) {
            return null;
        }

        var quotation = new StockQuotation();
        quotation.setStock(asset);
        quotation.setOpen(globalQuoteDto.getOpen());
        quotation.setHigh(globalQuoteDto.getHigh());
        quotation.setLow(globalQuoteDto.getLow());
        quotation.setClose(globalQuoteDto.getPrice());
        quotation.setVolume(globalQuoteDto.getVolume());
        quotation.setDate(globalQuoteDto.getLatestTradingDay());
        quotation.setPreviousClose(globalQuoteDto.getPreviousClose());
        quotation.setChange(globalQuoteDto.getChange());
        quotation.setChangePercent(globalQuoteDto.getChangePercent());
        return quotation;
    }

}
