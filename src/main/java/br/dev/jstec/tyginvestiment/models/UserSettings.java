package br.dev.jstec.tyginvestiment.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "users_settings",
        indexes = {
                @Index(name = "idx_user", columnList = "userId")
        })
public class UserSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String locale = "pt-BR";

    @Column(nullable = false)
    private String zoneTime = "America/Sao_Paulo";

    @Column(nullable = false)
    private String baseCurrency = "BRL";

    @Column(nullable = false)
    private int currencyDecimalPlaces = 2;

    @Column(nullable = false)
    private int decimalPlaces = 4;

    private boolean darkMode;

    @OneToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;
}
