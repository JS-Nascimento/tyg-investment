package br.dev.jstec.tyginvestiment.services.handlers;

import br.dev.jstec.tyginvestiment.dto.AccountDto;
import br.dev.jstec.tyginvestiment.enums.AccountType;
import br.dev.jstec.tyginvestiment.models.Account;
import br.dev.jstec.tyginvestiment.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountHandler {

    private final AccountRepository accountRepository;
    private final UserHandler userHandler;
    private final CurrencyHandler currencyHandler;

    public AccountDto saveAccount(AccountDto dto) {

        var entity = new Account();
        entity.setDescription(dto.getDescription());
        entity.setAccountType(AccountType.valueOf(dto.getAccountType()));
        entity.setBank(dto.getBank());
        entity.setInitialBalance(dto.getInitialBalance());
        entity.setAvailableBalance(dto.getAvailableBalance());
        entity.setTotalBalance(dto.getTotalBalance());
        entity.setCurrency(currencyHandler.getCurrencyByCode(dto.getCurrency()));
        entity.setUser(userHandler.getUserById(dto.getUserId()));

        var account = accountRepository.save(entity);

        return getAccountDto(account);
    }

    private static AccountDto getAccountDto(Account account) {
        var accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setDescription(account.getDescription());
        accountDto.setAccountType(account.getAccountType().name());
        accountDto.setBank(account.getBank());
        accountDto.setInitialBalance(account.getInitialBalance());
        accountDto.setAvailableBalance(account.getAvailableBalance());
        accountDto.setTotalBalance(account.getTotalBalance());
        accountDto.setCurrency(account.getCurrency().getCode());
        accountDto.setUserId(account.getUser().getId());
        return accountDto;
    }
}
