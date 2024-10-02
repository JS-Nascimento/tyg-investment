package br.dev.jstec.tyginvestiment.services.mappers;

import br.dev.jstec.tyginvestiment.dto.ConversionRateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConversionRateMapper {

    ConversionRateDto toDto(double rate, String rateDate);

    default String fromDto(ConversionRateDto dto) {
        return dto.getRateDate().toString();
    }

}
