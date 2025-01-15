package br.dev.jstec.tyginvestiment.repository;

import br.dev.jstec.tyginvestiment.models.BrapiAsset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrapiAssetRepository extends JpaRepository<BrapiAsset, String> {
}
