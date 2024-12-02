package br.dev.jstec.tyginvestiment.dto.user;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UserSettingsDto {

    private UUID userId;

    private Long id;
    private String locale;
    private String zoneTime;
    private String baseCurrency;
    private int currencyDecimalPlaces;
    private int decimalPlaces;
    private boolean darkMode;

}
