package br.dev.jstec.tyginvestiment.services.mappers;

import br.dev.jstec.tyginvestiment.dto.ConversionRateDto;
import br.dev.jstec.tyginvestiment.dto.CurrencyDto;
import br.dev.jstec.tyginvestiment.models.Currency;
import br.dev.jstec.tyginvestiment.services.handlers.ConversionRateHandler;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    @Mapping(target = "conversionRate", source = "conversionRate", ignore = true)
    CurrencyDto toDto(Currency entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "currencyBase", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    Currency toEntity(CurrencyDto dto);

}
