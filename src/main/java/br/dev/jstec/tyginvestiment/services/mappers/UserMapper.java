package br.dev.jstec.tyginvestiment.services.mappers;

import br.dev.jstec.tyginvestiment.dto.user.UserDto;
import br.dev.jstec.tyginvestiment.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User entity);

    User toEntity(UserDto dto);
}
