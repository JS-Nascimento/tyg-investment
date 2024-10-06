package br.dev.jstec.tyginvestiment.repository;

import br.dev.jstec.tyginvestiment.models.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    @Query("""
            SELECT DISTINCT a FROM Asset a
            """)
    Set<Asset> findDistinctSymbols();

    Optional<Asset> findBySymbol(String symbol);

}
