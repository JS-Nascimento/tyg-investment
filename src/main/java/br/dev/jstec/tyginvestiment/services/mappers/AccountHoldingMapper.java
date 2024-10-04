package br.dev.jstec.tyginvestiment.services.mappers;

import br.dev.jstec.tyginvestiment.dto.AccountHoldingDto;
import br.dev.jstec.tyginvestiment.dto.assetstype.StockDto;
import br.dev.jstec.tyginvestiment.models.AccountHolding;
import br.dev.jstec.tyginvestiment.models.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {AccountMapper.class, AssetMapper.class, CurrencyMapper.class, UserMapper.class})
public abstract class AccountHoldingMapper {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AssetMapper assetMapper;

    @ObjectFactory
    public AccountHolding toEntity(AccountHoldingDto dto) {
        AccountHolding accountHolding = new AccountHolding();

        if (dto.getAccount() != null) {
            accountHolding.setAccount(accountMapper.toEntity(dto.getAccount()));
        }

        accountHolding.setId(dto.getId());

        if (dto.getAsset() instanceof StockDto stockDto) {
            accountHolding.setAsset(assetMapper.toEntity(stockDto));
        }

        accountHolding.setInitialQuantity(dto.getInitialQuantity());
        accountHolding.setQuantity(dto.getQuantity());
        accountHolding.setInitialPrice(dto.getInitialPrice());
        accountHolding.setPurchaseDate(dto.getPurchaseDate());
        accountHolding.setDueDate(dto.getDueDate());
        return accountHolding;
    }

    @ObjectFactory
    public AccountHoldingDto toDto(AccountHolding entity) {
        AccountHoldingDto accountHoldingDto = new AccountHoldingDto();

        if (entity.getAccount() != null) {
            accountHoldingDto.setAccount(accountMapper.toDtoSimplified(entity.getAccount()));
        }

        accountHoldingDto.setId(entity.getId());

        if (entity.getAsset() != null && entity.getAsset() instanceof Stock stock) {
            accountHoldingDto.setAsset(assetMapper.toDtoSimplified(stock));
        }

        accountHoldingDto.setInitialQuantity(entity.getInitialQuantity());
        accountHoldingDto.setQuantity(entity.getQuantity());
        accountHoldingDto.setInitialPrice(entity.getInitialPrice());
        accountHoldingDto.setPurchaseDate(entity.getPurchaseDate());
        accountHoldingDto.setDueDate(entity.getDueDate());

        return accountHoldingDto;
    }

}
