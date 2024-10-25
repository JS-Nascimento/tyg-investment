package br.dev.jstec.tyginvestiment.services.handlers;

import br.dev.jstec.tyginvestiment.dto.user.ChangePasswordDto;
import br.dev.jstec.tyginvestiment.dto.user.UserDto;
import br.dev.jstec.tyginvestiment.exception.BusinessException;
import br.dev.jstec.tyginvestiment.models.User;
import br.dev.jstec.tyginvestiment.repository.UserRepository;
import br.dev.jstec.tyginvestiment.services.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static br.dev.jstec.tyginvestiment.exception.BusinessErrorMessage.NEW_PASSWORD_NOT_MATCH;
import static br.dev.jstec.tyginvestiment.exception.BusinessErrorMessage.OLD_PASSWORD_NOT_MATCH;

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
        return userRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void changePassword(UUID id, ChangePasswordDto dto) {
        var user = userRepository.findByTenantId(id)
                .stream()
                .filter(u -> !passwordEncoder.matches(dto.getOldPassword(), u.getPassword()))
                .findFirst()
                .orElseThrow(() -> new BusinessException(OLD_PASSWORD_NOT_MATCH));

        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new BusinessException(NEW_PASSWORD_NOT_MATCH);
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }
}
