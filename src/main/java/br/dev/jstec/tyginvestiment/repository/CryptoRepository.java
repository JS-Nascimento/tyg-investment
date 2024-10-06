package br.dev.jstec.tyginvestiment.repository;

import br.dev.jstec.tyginvestiment.models.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoRepository extends JpaRepository<Crypto, String> {
}
