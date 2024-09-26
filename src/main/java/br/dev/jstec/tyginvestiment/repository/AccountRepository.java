package br.dev.jstec.tyginvestiment.repository;

import br.dev.jstec.tyginvestiment.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
