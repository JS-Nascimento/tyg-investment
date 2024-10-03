package br.dev.jstec.tyginvestiment.services.mappers;

import br.dev.jstec.tyginvestiment.dto.AccountDto;
import br.dev.jstec.tyginvestiment.enums.AccountType;
import br.dev.jstec.tyginvestiment.models.Account;
import br.dev.jstec.tyginvestiment.models.Currency;
import br.dev.jstec.tyginvestiment.models.User;
import br.dev.jstec.tyginvestiment.services.handlers.CurrencyHandler;
import br.dev.jstec.tyginvestiment.services.handlers.UserHandler;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class AccountMapper {

    @Autowired
    private UserHandler userHandler;
    @Autowired
    private CurrencyHandler currencyHandler;

    @ObjectFactory
    public Account toEntity(AccountDto dto) {
        Account account = new Account();
        account.setId(dto.getId());
        account.setDescription(dto.getDescription());
        account.setAccountType(AccountType.valueOf(dto.getAccountType()));
        account.setBank(dto.getBank());
        account.setInitialBalance(dto.getInitialBalance());
        account.setTotalBalance(dto.getTotalBalance());
        account.setAvailableBalance(dto.getAvailableBalance());
        account.setUser(userHandler.getUserById(dto.getUserId()));
        account.setCurrency(currencyHandler.getCurrencyByCode(dto.getCurrency()));
        return account;
    }

    @ObjectFactory
    public AccountDto toDto(Account entity) {
        AccountDto accountDto = new AccountDto();
        accountDto.setId(entity.getId());
        accountDto.setDescription(entity.getDescription());
        accountDto.setAccountType(entity.getAccountType().name());
        accountDto.setBank(entity.getBank());
        accountDto.setInitialBalance(entity.getInitialBalance());
        accountDto.setTotalBalance(entity.getTotalBalance());
        accountDto.setAvailableBalance(entity.getAvailableBalance());
        accountDto.setUserId(entity.getUser().getId());
        accountDto.setCreatedBy(entity.getCreatedBy());
        accountDto.setCreatedDate(entity.getCreatedDate());
        accountDto.setLastModifiedBy(entity.getLastModifiedBy());
        accountDto.setLastModifiedDate(entity.getLastModifiedDate());
        accountDto.setCurrency(entity.getCurrency().getCode());
        return accountDto;
    }
}
