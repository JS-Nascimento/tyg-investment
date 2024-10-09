package br.dev.jstec.tyginvestiment.repository;

import br.dev.jstec.tyginvestiment.dto.accountsummary.PortfolioOverviewDto;
import br.dev.jstec.tyginvestiment.models.AccountHolding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface AccountHoldingRepository extends JpaRepository<AccountHolding, Long> {

    @Query(name = "AccountHoldingRepository.findAccountHoldingWithDetails", nativeQuery = true)
    List<PortfolioOverviewDto> findAccountHoldingWithDetails(@Param("accountId") Long accountId);

    @Query("""
            select ah from AccountHolding ah
            where ah.account.id = :accountId
            and ah.asset.symbol = :assetId
            """)
    Optional<AccountHolding> findAccountHoldingByAccountIdAndAssetId(@Param("accountId") Long accountId, @Param("assetId") String assetId);

}
