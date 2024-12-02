package br.dev.jstec.tyginvestiment.repository;

import br.dev.jstec.tyginvestiment.models.UserCurrencies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCurrenciesRepository extends JpaRepository<UserCurrencies, Long> {

    boolean existsByUser_IdAndCurrency_Id(Long userId, Long currencyId);
}