package br.dev.jstec.tyginvestiment.repository;

import br.dev.jstec.tyginvestiment.models.AccountHolding;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountHoldingRepository extends JpaRepository<AccountHolding, Long> {
}
