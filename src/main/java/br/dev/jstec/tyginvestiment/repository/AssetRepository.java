package br.dev.jstec.tyginvestiment.repository;

import br.dev.jstec.tyginvestiment.models.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, String> {
}
