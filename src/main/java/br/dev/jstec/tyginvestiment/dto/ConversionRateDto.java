package br.dev.jstec.tyginvestiment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ConversionRateDto {
    private double rate;
    private LocalDateTime rateDate;
}
