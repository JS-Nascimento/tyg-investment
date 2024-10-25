package br.dev.jstec.tyginvestiment.services.handlers;

import br.dev.jstec.tyginvestiment.dto.UserDto;
import br.dev.jstec.tyginvestiment.exception.InfrastructureException;
import br.dev.jstec.tyginvestiment.models.User;
import br.dev.jstec.tyginvestiment.repository.UserRepository;
import br.dev.jstec.tyginvestiment.services.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static br.dev.jstec.tyginvestiment.exception.ErrorMessage.USER_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto saveUser(UserDto dto) {

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        var entity = mapper.toEntity(dto);

        var user = userRepository.save(entity);

        return mapper.toDto(user);
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new InfrastructureException(USER_NOT_FOUND));
    }

    @Cacheable(value = "me", key = "#tenantId")
    @Transactional(readOnly = true)
    public UserDto getUserByTenantId(UUID tenantId) {
        return userRepository.findByTenantId(tenantId)
                .stream()
                .findFirst()
                .map(mapper::toDto)
                .orElseThrow(() -> new InfrastructureException(USER_NOT_FOUND));
    }
}
