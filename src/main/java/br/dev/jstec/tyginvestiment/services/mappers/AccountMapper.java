package br.dev.jstec.tyginvestiment.services.mappers;

import br.dev.jstec.tyginvestiment.dto.AccountDto;
import br.dev.jstec.tyginvestiment.dto.accountsummary.InvestmentDto;
import br.dev.jstec.tyginvestiment.dto.accountsummary.InvestmentSummaryStatementDto;
import br.dev.jstec.tyginvestiment.enums.AccountType;
import br.dev.jstec.tyginvestiment.models.Account;
import br.dev.jstec.tyginvestiment.services.handlers.CurrencyHandler;
import br.dev.jstec.tyginvestiment.services.handlers.UserHandler;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

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

    @ObjectFactory
    public AccountDto toDtoSimplified(Account entity) {
        AccountDto accountDto = new AccountDto();
        accountDto.setId(entity.getId());
        accountDto.setAccountType(entity.getAccountType().name());
        accountDto.setBank(entity.getBank());
        accountDto.setCurrency(entity.getCurrency().getCode());
        return accountDto;
    }

    @ObjectFactory
    public InvestmentSummaryStatementDto toInvestmentSummaryStatementDto(AccountDto accountDto, List<InvestmentDto> investments) {
        InvestmentSummaryStatementDto dto = new InvestmentSummaryStatementDto();
        dto.setId(accountDto.getId());
        dto.setDescription(accountDto.getDescription());
        dto.setBank(accountDto.getBank());
        dto.setAccountType(accountDto.getAccountType());
        dto.setCurrency(accountDto.getCurrency());
        dto.setInitialBalance(accountDto.getInitialBalance());
        dto.setAvailableBalance(accountDto.getAvailableBalance());

        if (investments == null || investments.isEmpty()) {
            return dto;
        }

        var totalInvested = investments.stream()
                .map(InvestmentDto::getTotalInvested)
                .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP);

        dto.setTotalBalance(totalInvested);

        dto.setInvestments(investments);
        return dto;
    }
}
