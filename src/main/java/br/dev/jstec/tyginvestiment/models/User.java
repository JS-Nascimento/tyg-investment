package br.dev.jstec.tyginvestiment.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "users",
        indexes = {
                @Index(name = "idx_tenant", columnList = "tenantId"),
                @Index(name = "idx_email", columnList = "email")
        })
public class User extends Auditable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false, unique = true)
    private UUID tenantId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 4)
    private String baseCurrency;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean enabled = true;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean accountNonExpired = true;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean isAccountNonLocked = true;

    @PrePersist
    protected void onCreate() {
        if (tenantId == null) {
            tenantId = UUID.randomUUID();
        }
    }
}
