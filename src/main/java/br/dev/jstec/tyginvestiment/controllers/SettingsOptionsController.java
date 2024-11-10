package br.dev.jstec.tyginvestiment.controllers;

import br.dev.jstec.tyginvestiment.dto.settings.LocaleDto;
import br.dev.jstec.tyginvestiment.dto.settings.ZoneTimeDto;
import br.dev.jstec.tyginvestiment.utils.SettingsOptionsUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Locale.getDefault;
import static java.util.Locale.of;
import static java.util.Objects.nonNull;

@Slf4j
@RestController
@RequestMapping("/api/v1/settings/options")
@RequiredArgsConstructor
public class SettingsOptionsController {

    private final SettingsOptionsUtils settingsOptionsUtils;

    @GetMapping(path = "/timezones")
    public ResponseEntity<List<ZoneTimeDto>> getListZoneTime(@RequestParam(name = "locale", required = false) String locale) {

        return ResponseEntity.ok(settingsOptionsUtils
                .getZoneTimeList(
                        nonNull(locale) ? of(locale) : getDefault())
        );
    }

    @GetMapping(path = "/locales")
    public ResponseEntity<List<LocaleDto>> getListLocales() {

        return ResponseEntity.ok(settingsOptionsUtils
                .getLocales()
        );
    }

}
