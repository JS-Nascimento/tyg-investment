package br.dev.jstec.tyginvestiment.dto.currencies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyQuotationHistoryDto {

    private Double averageRate;
    private Double maxRate;
    private Double minRate;
    private Date dateOnly;

}
