package br.dev.jstec.tyginvestiment.utils;

import br.dev.jstec.tyginvestiment.dto.settings.LocaleDto;
import br.dev.jstec.tyginvestiment.dto.settings.ZoneTimeDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;

import static java.time.format.TextStyle.NARROW;
import static org.apache.commons.lang3.StringUtils.capitalize;

@Component
public class SettingsOptionsUtils {

    private static final List<String> ZONES_POPULARES = Arrays.asList(
            "America/Sao_Paulo", "America/New_York", "Europe/London", "Europe/Berlin",
            "Asia/Shanghai", "Asia/Dubai", "Asia/Singapore", "Asia/Hong_Kong",
            "America/Mexico_City", "America/Chicago", "America/Toronto",
            "America/Buenos_Aires", "America/Santiago", "America/Lima",
            "America/Cuiaba", "Europe/Moscow", "Europe/Istanbul", "Europe/Athens",
            "Europe/Rome", "Europe/Budapest", "Europe/Warsaw", "Europe/Prague",
            "Asia/Tokyo", "Europe/Paris", "Australia/Sydney",
            "America/Los_Angeles", "Africa/Johannesburg", "UTC"
    );


    @Deprecated
    public static List<ZoneTimeDto> getZoneTimeList() {
        return ZoneId.getAvailableZoneIds().stream()
                .map(ZoneId::of)
                .map(zone -> {
                    ZonedDateTime now = ZonedDateTime.now(zone);
                    ZoneOffset offset = now.getOffset();
                    String nomeUTC = String.format("%s (UTC%s)", zone, offset);
                    return new ZoneTimeDto(nomeUTC, zone);
                })
                .sorted((z1, z2) -> z1.description().compareTo(z2.description()))
                .toList();
    }

    @Cacheable("zoneTimeList")
    public List<ZoneTimeDto> getZoneTimeList(Locale locale) {
        Map<ZoneOffset, ZoneTimeDto> filteredZones = new LinkedHashMap<>();

        for (String zoneId : ZONES_POPULARES) {
            ZoneId zone = ZoneId.of(zoneId);
            ZonedDateTime now = ZonedDateTime.now(zone).withMonth(1);
            ZoneOffset offset = now.getOffset();

            String nomeTraduzido = zone.getDisplayName(NARROW, locale);
            String nomeUTC = String.format("%s (UTC%s)", nomeTraduzido, offset)
                    .replace("_", " ");

            filteredZones.putIfAbsent(offset, new ZoneTimeDto(nomeUTC, zone));
        }

        for (String zoneId : ZoneId.getAvailableZoneIds()) {
            ZoneId zone = ZoneId.of(zoneId);
            ZonedDateTime now = ZonedDateTime.now(zone).withMonth(1);
            ZoneOffset offset = now.getOffset();

            if (!filteredZones.containsKey(offset)) {
                String nomeTraduzido = zone.getDisplayName(NARROW, locale);
                String nomeUTC = String.format("%s (UTC%s)", nomeTraduzido, offset)
                        .replace("_", " ");
                filteredZones.put(offset, new ZoneTimeDto(nomeUTC, zone));
            }
        }

        return filteredZones.values().stream()
                .filter(zoneTimeDto -> !zoneTimeDto.description().contains("Etc")
                        && !zoneTimeDto.description().contains("NZ"))
                .sorted(Comparator.comparing(ZoneTimeDto::description))
                .toList();
    }

    public List<LocaleDto> getCompleteLocales() {

        Set<String> commonLanguages = Set.of("en", "es", "fr", "pt", "de", "zh");

        return Arrays.stream(Locale.getAvailableLocales())
                .filter(locale -> commonLanguages.contains(locale.getLanguage()))
                .filter(locale -> !locale.getCountry().isEmpty())
                .map(locale -> new LocaleDto(locale.getDisplayName(), locale.toLanguageTag()))
                .distinct()
                .sorted(Comparator.comparing(LocaleDto::description))
                .toList();

    }

    public List<LocaleDto> getLocales() {
        Set<String> primaryLocales = Set.of("pt-BR", "pt-PT", "en-US", "en-GB", "es-ES", "fr-FR", "de-DE");

        return Arrays.stream(Locale.getAvailableLocales())
                .filter(locale -> primaryLocales.contains(locale.toLanguageTag()))
                .map(locale -> new LocaleDto(capitalize(locale.getDisplayName()), locale.toLanguageTag()))
                .sorted(Comparator.comparing(LocaleDto::description))
                .toList();
    }
}




