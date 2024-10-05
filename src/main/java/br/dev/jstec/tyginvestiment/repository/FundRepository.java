package br.dev.jstec.tyginvestiment.repository;

import br.dev.jstec.tyginvestiment.models.Fund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundRepository extends JpaRepository<Fund, String> {
}
