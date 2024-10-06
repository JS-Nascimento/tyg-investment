package br.dev.jstec.tyginvestiment.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

import static br.dev.jstec.tyginvestiment.exception.BaseErrorMessage.REQUEST_ERROR;
import static java.text.MessageFormat.format;

@Getter
public class RequestException extends RuntimeException {

    private final HttpStatusCode httpStatus;
    private final ErrorMessage errorMessage;
    private final String serviceName;

    public RequestException(HttpStatusCode httpStatus, ErrorMessage errorMessage, String serviceName) {

        super(format(REQUEST_ERROR.getMsg(), serviceName, httpStatus, errorMessage));
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
        this.serviceName = serviceName;
    }
}
