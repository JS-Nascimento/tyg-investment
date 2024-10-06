package br.dev.jstec.tyginvestiment.exception;

import lombok.Getter;

import static java.text.MessageFormat.format;

@Getter
public class InfrastructureException extends RuntimeException {

    private final ErrorMessage errorMessage;
    private final String serviceName;

    public InfrastructureException(ErrorMessage errorMessage) {

        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
        this.serviceName = null;
    }

    public InfrastructureException(ErrorMessage errorMessage, String serviceName,
                                   Throwable cause) {

        super(format(errorMessage.getMessage(), serviceName), cause);
        this.errorMessage = errorMessage;
        this.serviceName = serviceName;
    }

    public InfrastructureException(ErrorMessage errorMessage, String serviceName) {

        super(format(errorMessage.getMessage(), serviceName));
        this.errorMessage = errorMessage;
        this.serviceName = serviceName;
    }
}
