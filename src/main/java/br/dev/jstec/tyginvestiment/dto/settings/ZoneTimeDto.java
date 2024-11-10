package br.dev.jstec.tyginvestiment.dto.settings;

import java.time.ZoneId;

public record ZoneTimeDto(String description, ZoneId zoneId) {
}
