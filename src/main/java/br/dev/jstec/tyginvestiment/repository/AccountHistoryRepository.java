package br.dev.jstec.tyginvestiment.repository;

import br.dev.jstec.tyginvestiment.models.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountHistoryRepository extends JpaRepository<AccountHistory, Long>, JpaSpecificationExecutor<AccountHistory> {
}
