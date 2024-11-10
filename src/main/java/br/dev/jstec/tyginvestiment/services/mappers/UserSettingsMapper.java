package br.dev.jstec.tyginvestiment.services.mappers;

import br.dev.jstec.tyginvestiment.dto.user.UserSettingsDto;
import br.dev.jstec.tyginvestiment.models.UserSettings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserSettingsMapper {

    @Mapping(target = "userId", source = "user.id")
    UserSettingsDto toDto(UserSettings entity);

    @Mapping(target = "user.id", source = "userId")
    UserSettings toEntity(UserSettingsDto dto);

    default UserSettings updateEntityFromDto(UserSettingsDto dto, UserSettings entity) {

        if (dto.getLocale() != null) {
            entity.setLocale(dto.getLocale());
        }
        if (dto.getZoneTime() != null) {
            entity.setZoneTime(dto.getZoneTime());
        }
        if (dto.getBaseCurrency() != null) {
            entity.setBaseCurrency(dto.getBaseCurrency());
        }
        entity.setDarkMode(dto.isDarkMode());

        if (dto.getDecimalPlaces() != 0) {
            entity.setDecimalPlaces(dto.getDecimalPlaces());
        }

        if (dto.getCurrencyDecimalPlaces() != 0) {
            entity.setCurrencyDecimalPlaces(dto.getCurrencyDecimalPlaces());
        }

        return entity;
    }
}
