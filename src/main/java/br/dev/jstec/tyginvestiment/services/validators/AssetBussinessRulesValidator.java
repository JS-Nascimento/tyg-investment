package br.dev.jstec.tyginvestiment.services.validators;

import br.dev.jstec.tyginvestiment.clients.dto.AlphaVantageInformation;
import br.dev.jstec.tyginvestiment.exception.InfrastructureException;

import static br.dev.jstec.tyginvestiment.exception.ErrorMessage.ALPHA_VANTAGE_REQUEST_ERROR;
import static br.dev.jstec.tyginvestiment.exception.ErrorMessage.API_LIMIT_REACHED;
import static java.util.Objects.nonNull;

public class AssetBussinessRulesValidator {

    public static <T extends AlphaVantageInformation> void validateClientApiResponse(T asset) {

        if (nonNull(asset.getInformation())) {
            throw new InfrastructureException(API_LIMIT_REACHED, "AlphaVantage");
        }
        if (nonNull(asset.getErrorMessage())) {
            throw new InfrastructureException(ALPHA_VANTAGE_REQUEST_ERROR, asset.getErrorMessage());
        }
    }

}
