package br.dev.jstec.tyginvestiment.services.handlers;

import br.dev.jstec.tyginvestiment.dto.AccountDto;
import br.dev.jstec.tyginvestiment.repository.AccountRepository;
import br.dev.jstec.tyginvestiment.services.mappers.AccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountHandler {

    private final AccountRepository accountRepository;
    private final AccountMapper mapper;

    @Transactional
    public AccountDto saveAccount(AccountDto dto) {

        var entity = mapper.toEntity(dto);

        var account = accountRepository.save(entity);

        return mapper.toDto(account);
    }

    @Transactional(readOnly = true)
    public AccountDto findById(Long id) {
        var account = accountRepository.findById(id).orElse(null);

        return mapper.toDto(account);
    }
}
