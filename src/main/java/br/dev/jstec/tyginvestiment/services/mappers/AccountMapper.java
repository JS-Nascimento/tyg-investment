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
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {UserMapper.class, CurrencyMapper.class})
public interface AccountMapper {

    @Mapping(target = "userId", source = "java(entity.getUser().getId())")
    AccountDto toDto(Account entity);


    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "accountType", source = "type", qualifiedByName = "stringToAccountType")
    @Mapping(target = "currency", source = "currency", qualifiedByName = "currencyByCode")
    @Mapping(target = "user", source = "userId", qualifiedByName = "userById")
    Account toEntity(AccountDto dto);

    @Named("stringToAccountType")
    default AccountType stringToAccountType(String accountType) {
        return AccountType.valueOf(accountType);
    }

    @Named("currencyByCode")
    default Currency getCurrencyByCode(String currency, @Context CurrencyHandler currencyHandler) {
        return currencyHandler.getCurrencyByCode(currency);
    }

    @Named("userById")
    default Long getUserById(Long userId, @Context UserHandler userHandler) {
        return userHandler.getUserById(userId).getId();
    }

}
