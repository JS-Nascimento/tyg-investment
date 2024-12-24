package br.dev.jstec.tyginvestiment.clients.brapi.dto;


import lombok.Data;

@Data
public class AssetListDto {
    private String stock;
    private String name;
    private Double close;
    private Double change;
    private Long volume;
    private Double marketCap;
    private String logo;
    private String sector;
    private String type;
}
