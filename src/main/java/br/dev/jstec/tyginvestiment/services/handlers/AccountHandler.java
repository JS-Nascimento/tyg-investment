package br.dev.jstec.tyginvestiment.services.handlers;

import br.dev.jstec.tyginvestiment.dto.AccountDto;
import br.dev.jstec.tyginvestiment.dto.accountsummary.InvestmentDto;
import br.dev.jstec.tyginvestiment.dto.accountsummary.InvestmentSummaryStatementDto;
import br.dev.jstec.tyginvestiment.dto.accountsummary.PortfolioOverviewDto;
import br.dev.jstec.tyginvestiment.exception.ErrorMessage;
import br.dev.jstec.tyginvestiment.exception.InfrastructureException;
import br.dev.jstec.tyginvestiment.models.AssetTransaction;
import br.dev.jstec.tyginvestiment.repository.AccountHoldingRepository;
import br.dev.jstec.tyginvestiment.repository.AccountRepository;
import br.dev.jstec.tyginvestiment.services.mappers.AccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountHandler {

    private final AccountRepository accountRepository;
    private final AccountHoldingRepository accountHoldingRepository;
    private final AccountMapper mapper;

    @Transactional
    public AccountDto saveAccount(AccountDto dto) {

        var entity = mapper.toEntity(dto);

        var account = accountRepository.save(entity);

        return mapper.toDto(account);
    }

    @Transactional(readOnly = true)
    public AccountDto findById(Long id) {
        var account = accountRepository
                .findById(id)
                .orElseThrow(() -> new InfrastructureException(ErrorMessage.ACCOUNT_NOT_FOUND, String.valueOf(id)));
        return mapper.toDto(account);
    }

    @Transactional(readOnly = true)
    public InvestmentSummaryStatementDto getAccountSummary(Long id) {
        var account = findById(id);

        if (account == null) {
            return null;
        }

        var investments = getInvestments(id);

        return mapper.toInvestmentSummaryStatementDto(account, investments);
    }

    @Transactional(readOnly = true)
    public List<InvestmentDto> getInvestments(Long accountId) {
        var holdings = accountHoldingRepository.findAccountHoldingWithDetails(accountId);

        if (holdings == null || holdings.isEmpty()) {
            return null;
        }

        return holdings.stream()
                .collect(groupingBy(PortfolioOverviewDto::getType))
                .entrySet()
                .stream()
                .map(entry -> {
                    var type = entry.getKey();
                    var overview = entry.getValue();

                    var totalInvested = overview.stream()
                            .map(PortfolioOverviewDto::getValue)
                            .filter(Objects::nonNull)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    return new InvestmentDto(type, overview, totalInvested);
                })
                .toList();
    }

    @Transactional
    public void updateAccountAfterTransaction(AssetTransaction transaction) {
        log.info("Updating account after transaction: {}", transaction.getId());
        var account = accountRepository.findById(transaction.getAccount().getId())
                .orElseThrow(() -> new InfrastructureException(ErrorMessage.ACCOUNT_NOT_FOUND,
                        String.valueOf(transaction.getAccount().getId())));

        switch (transaction.getTransactionType()) {
            case BUY:
                account.setAvailableBalance(account.getAvailableBalance().subtract(transaction.getValue()));
                break;
            case SELL, DIVIDEND:
                account.setAvailableBalance(account.getAvailableBalance().add(transaction.getValue()));
                break;
            default:
                throw new InfrastructureException(ErrorMessage.TRANSACTION_TYPE_NOT_FOUND);
        }

        var totalInvested = getInvestments((transaction.getAccount().getId()))
                .stream()
                .map(InvestmentDto::getTotalInvested)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        var totalBalance = account.getAvailableBalance().add(totalInvested);

        account.setTotalBalance(totalBalance);

        accountRepository.save(account);
    }

}
