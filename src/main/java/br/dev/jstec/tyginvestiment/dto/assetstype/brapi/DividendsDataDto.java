package br.dev.jstec.tyginvestiment.dto.assetstype.brapi;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data

public class DividendsDataDto {

    private Long id;

    private List<CashDividendDto> cashDividends = new ArrayList<>();

    private List<Object> stockDividends = new ArrayList<>();

    private List<Object> subscriptions = new ArrayList<>();
}
