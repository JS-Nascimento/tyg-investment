package br.dev.jstec.tyginvestiment.repository;

import br.dev.jstec.tyginvestiment.models.UserSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSettingsRepository extends JpaRepository<UserSettings, Long> {

}