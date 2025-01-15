package br.dev.jstec.tyginvestiment.models.brapiassets;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "summary_profile")
public class SummaryProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String phone;
    private String website;
    private String industry;
    private String industryKey;
    private String industryDisp;
    private String sector;
    private String sectorKey;
    private String sectorDisp;
    @Column(length = 2000)
    private String longBusinessSummary;
}