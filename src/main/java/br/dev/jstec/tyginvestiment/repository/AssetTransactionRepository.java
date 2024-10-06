package br.dev.jstec.tyginvestiment.repository;

import br.dev.jstec.tyginvestiment.models.AssetTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetTransactionRepository extends JpaRepository<AssetTransaction, Long> {

}
