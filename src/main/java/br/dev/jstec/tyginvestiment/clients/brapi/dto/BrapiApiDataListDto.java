package br.dev.jstec.tyginvestiment.clients.brapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class BrapiApiDataListDto {
    private List<IndexListDto> indexes;
    private List<AssetListDto> stocks;
    private List<String> availableSectors;
    private List<String> availableStockTypes;
}
