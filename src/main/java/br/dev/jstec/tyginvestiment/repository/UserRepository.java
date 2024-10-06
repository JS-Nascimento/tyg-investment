package br.dev.jstec.tyginvestiment.repository;

import br.dev.jstec.tyginvestiment.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByTenantId(UUID tenantId);
}
