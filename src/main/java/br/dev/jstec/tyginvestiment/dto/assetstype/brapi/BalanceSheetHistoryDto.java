package br.dev.jstec.tyginvestiment.dto.assetstype.brapi;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BalanceSheetHistoryDto {

    private Long id;

    private List<BalanceSheetStatementDto> balanceSheetStatements = new ArrayList<>();

}
