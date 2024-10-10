package br.dev.jstec.tyginvestiment.services.mappers;

import br.dev.jstec.tyginvestiment.dto.AccountDto;
import br.dev.jstec.tyginvestiment.dto.AccountHistoryDto;
import br.dev.jstec.tyginvestiment.enums.OperationType;
import br.dev.jstec.tyginvestiment.enums.TransactionType;
import br.dev.jstec.tyginvestiment.models.AccountHistory;
import br.dev.jstec.tyginvestiment.models.AssetTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {AccountMapper.class, AssetMapper.class, CurrencyMapper.class, UserMapper.class})
public abstract class AccountHistoryMapper {

    @Autowired
    private AccountMapper accountMapper;

    @ObjectFactory
    public AccountHistory toEntity(AccountHistoryDto dto, AccountDto accountDto) {
        AccountHistory accountHistory = new AccountHistory();

        if (accountDto != null) {
            accountHistory.setAccount(accountMapper.toEntity(accountDto));
        }

        accountHistory.setId(dto.getId());
        accountHistory.setHistoryDescription(dto.getHistoryDescription());
        accountHistory.setOperation(OperationType.fromString(dto.getOperation()));
        accountHistory.setAssetSymbol(dto.getAssetSymbol());
        accountHistory.setQuantity(dto.getQuantity());
        accountHistory.setValue(dto.getValue());
        return accountHistory;
    }

    @ObjectFactory
    public AccountHistoryDto toDto(AccountHistory entity) {
        AccountHistoryDto accountHoldingDto = new AccountHistoryDto();

        if (entity.getAccount() != null) {
            accountHoldingDto.setAccountId(entity.getAccountId());
        }

        accountHoldingDto.setId(entity.getId());
        accountHoldingDto.setHistoryDescription(entity.getHistoryDescription());
        accountHoldingDto.setOperation(entity.getOperation().name());
        accountHoldingDto.setAssetSymbol(entity.getAssetSymbol());
        accountHoldingDto.setQuantity(entity.getQuantity());
        accountHoldingDto.setValue(entity.getValue());
        return accountHoldingDto;
    }

    @ObjectFactory
    public AccountHistory toEntity(AssetTransaction transaction) {
        AccountHistory accountHistory = new AccountHistory();

        if (transaction.getAccount() != null) {
            accountHistory.setAccount(transaction.getAccount());
        }

        if (transaction.getTransactionType().equals(TransactionType.BUY)) {
            accountHistory.setHistoryDescription("Compra de " + transaction.getAsset().getSymbol());
            accountHistory.setOperation(OperationType.DEBIT);
        } else if (transaction.getTransactionType().equals(TransactionType.SELL)) {
            accountHistory.setHistoryDescription("Venda de " + transaction.getAsset().getSymbol());
            accountHistory.setOperation(OperationType.CREDIT);
        } else {
            accountHistory.setHistoryDescription("Transferência de " + transaction.getAsset().getSymbol());
            accountHistory.setOperation(OperationType.CREDIT);
        }

        accountHistory.setAssetSymbol(transaction.getAsset().getSymbol());
        accountHistory.setQuantity(transaction.getQuantity());
        accountHistory.setValue(transaction.getValue());
        return accountHistory;
    }
}
