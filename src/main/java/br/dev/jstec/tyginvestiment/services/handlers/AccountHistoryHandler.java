package br.dev.jstec.tyginvestiment.services.handlers;

import br.dev.jstec.tyginvestiment.dto.AccountHistoryDto;
import br.dev.jstec.tyginvestiment.models.AssetTransaction;
import br.dev.jstec.tyginvestiment.models.specification.AccountHistorySpecifications;
import br.dev.jstec.tyginvestiment.repository.AccountHistoryRepository;
import br.dev.jstec.tyginvestiment.services.mappers.AccountHistoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AccountHistoryHandler {

    private final AccountHistoryRepository repository;
    private final AccountHandler accountHandler;
    private final AccountHistoryMapper mapper;

    @Transactional
    public void create(AccountHistoryDto dto) {

        var account = accountHandler.findById(dto.getAccountId());
        var entity = mapper.toEntity(dto, account);

        repository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<AccountHistoryDto> findBetweenCreateDate(LocalDate startDate, LocalDate endDate) {

        var spec = AccountHistorySpecifications.hasDateBetween(startDate, endDate);
        var histories = repository.findAll(spec);

        return histories.stream()
                .filter(Objects::nonNull)
                .map(mapper::toDto)
                .sorted((h1, h2) -> h2.getCreatedDate().compareTo(h1.getCreatedDate()))
                .toList();
    }

    @Transactional
    public void createAccountHistoryByTransaction(AssetTransaction transaction) {
        var entity = mapper.toEntity(transaction);
        repository.saveAndFlush(entity);
    }
}
