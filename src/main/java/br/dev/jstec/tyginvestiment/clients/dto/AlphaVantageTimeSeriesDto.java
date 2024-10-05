package br.dev.jstec.tyginvestiment.clients.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class AlphaVantageTimeSeriesDto extends AlphaVantageInformation {

    @JsonProperty("Meta Data")
    private MetaDataDto metaData;

    @JsonProperty("Time Series (Daily)")
    private Map<LocalDate, DailyPriceDto> timeSeriesDaily;
}
