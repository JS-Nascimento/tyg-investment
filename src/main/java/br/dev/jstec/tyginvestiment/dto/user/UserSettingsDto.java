package br.dev.jstec.tyginvestiment.dto.user;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSettingsDto {

    private Long userId;

    private Long id;
    private String locale;
    private String zoneTime;
    private String baseCurrency;
    private int currencyDecimalPlaces;
    private int decimalPlaces;
    private boolean darkMode;

}
