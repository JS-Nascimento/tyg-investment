package br.dev.jstec.tyginvestiment.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class CurrencyDto {


    private String code;

    private String name;

    private String symbol;

    private Integer decimalPlaces;

    private boolean currencyBase;

    private ConversionRateDto conversionRate;
}
