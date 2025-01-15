package br.dev.jstec.tyginvestiment.dto.assetstype.brapi;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class IncomeStatementHistoryDto {

    private Long id;

    private List<IncomeStatementDto> incomeStatementHistory = new ArrayList<>();
}
