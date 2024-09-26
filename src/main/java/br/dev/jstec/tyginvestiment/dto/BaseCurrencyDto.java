package br.dev.jstec.tyginvestiment.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class BaseCurrencyDto {

    private String code;

    private String name;

    private String symbol;

    private List<CurrencyDto> currencies;

}
