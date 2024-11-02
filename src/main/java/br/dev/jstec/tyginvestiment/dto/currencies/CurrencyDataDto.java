package br.dev.jstec.tyginvestiment.dto.currencies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDataDto {
    private String code;
    private String description;
}
