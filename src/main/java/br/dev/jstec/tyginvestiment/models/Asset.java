package br.dev.jstec.tyginvestiment.models;

import br.dev.jstec.tyginvestiment.enums.AssetType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE", discriminatorType = DiscriminatorType.STRING)
@Table(name = "assets")
@NoArgsConstructor
public abstract class Asset extends Auditable<Long>{

    @Id
    private String symbol;

    @Enumerated(EnumType.STRING)
    private AssetType assetType;

    @Column(length = 100)
    private String name;

    @Column(length = 2000)
    private String description;

    private String currency;
}
