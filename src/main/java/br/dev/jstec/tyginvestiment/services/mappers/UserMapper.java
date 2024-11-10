package br.dev.jstec.tyginvestiment.services.mappers;

import br.dev.jstec.tyginvestiment.dto.user.UserDto;
import br.dev.jstec.tyginvestiment.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserSettingsMapper.class})
public interface UserMapper {

    @Mapping(target = "userSettings", source = "userSettings")
    UserDto toDto(User entity);

    User toEntity(UserDto dto);
}
