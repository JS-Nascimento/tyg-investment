package br.dev.jstec.tyginvestiment.dto;

import lombok.Data;

@Data
public class CurrencyDto {


    private String code;

    private String name;

    private String symbol;

    private int decimalPlaces;

    private boolean currencyBase;

    private ConversionRateDto conversionRate;
}
