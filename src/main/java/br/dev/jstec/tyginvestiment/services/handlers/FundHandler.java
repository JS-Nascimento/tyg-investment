package br.dev.jstec.tyginvestiment.services.handlers;

import br.dev.jstec.tyginvestiment.clients.AlphaClient;
import br.dev.jstec.tyginvestiment.clients.dto.EtfProfileDto;
import br.dev.jstec.tyginvestiment.config.ApiKeyManager;
import br.dev.jstec.tyginvestiment.dto.assetstype.FundDto;
import br.dev.jstec.tyginvestiment.enums.AssetType;
import br.dev.jstec.tyginvestiment.events.AssetSavedEvent;
import br.dev.jstec.tyginvestiment.exception.InfrastructureException;
import br.dev.jstec.tyginvestiment.models.Fund;
import br.dev.jstec.tyginvestiment.repository.FundRepository;
import br.dev.jstec.tyginvestiment.services.mappers.AssetMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static br.dev.jstec.tyginvestiment.exception.ErrorMessage.ASSET_NOT_FOUND;
import static br.dev.jstec.tyginvestiment.exception.ErrorMessage.ATTRIBUTE_NOT_FOUND;
import static br.dev.jstec.tyginvestiment.services.validators.AssetBussinessRulesValidator.validateClientApiResponse;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Component("FUND")
@RequiredArgsConstructor
@Slf4j
public class FundHandler implements AssetHandler<Fund, FundDto> {

    private final AssetMapper mapper;
    private final FundRepository fundRepository;
    private final AlphaClient alphaClient;
    private final CurrencyHandler currencyHandler;
    private final AssetHistoryHandler assetHistoryHandler;

    private final ApplicationEventPublisher publisher;

    private final ApiKeyManager apiKeyManager;

    @Transactional(timeout = 300)
    @Override
    public FundDto save(String symbol, String currency) {

        if (isBlank(symbol)) {
            throw new InfrastructureException(ATTRIBUTE_NOT_FOUND, "SIMBOLO");
        }

        if (isBlank(currency)) {
            throw new InfrastructureException(ATTRIBUTE_NOT_FOUND, "MOEDA");
        }

        var asset = getAsset(symbol);

        if (isNull(asset)) {
            throw new InfrastructureException(ASSET_NOT_FOUND, symbol);
        }

        currencyHandler.verifyAndSaveIfNotExists(currency);

        completeFundInfo(asset, symbol, currency);

        var entity = mapper.toEntity(asset);

        var entitySaved = fundRepository.save(entity);

        if (nonNull(entitySaved.getSymbol())) {
            publisher.publishEvent(new AssetSavedEvent(this, entitySaved));
        }

        return mapper.toDto(entitySaved);
    }

    @Override
    @Transactional(readOnly = true)
    public FundDto findById(String symbol) {
        return fundRepository.findById(symbol)
                .map(mapper::toDto)
                .orElseThrow(() -> new InfrastructureException(ASSET_NOT_FOUND, symbol));
    }

    @Override
    public FundDto save(FundDto dto) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public FundDto save(String symbol) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Transactional
    public EtfProfileDto getAsset(String symbol) {

        var asset = alphaClient.getEtfProfile(symbol, apiKeyManager.getAvailableApiKey());

        validateClientApiResponse(asset);

        if (isNull(asset) || isBlank(asset.getSymbol())) {
            throw new InfrastructureException(ASSET_NOT_FOUND, symbol);
        }

        return asset;
    }

    private void completeFundInfo(EtfProfileDto dto, String symbol, String Currency) {
        dto.setSymbol(symbol);
        dto.setAssetType(AssetType.FUND);
        dto.setName(symbol);
        dto.setCurrency(Currency);
    }
}
