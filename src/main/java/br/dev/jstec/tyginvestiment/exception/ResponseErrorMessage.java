package br.dev.jstec.tyginvestiment.exception;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
@Getter
@Setter
@JsonInclude(NON_NULL)
public class ResponseErrorMessage {

    int errorCode;
    String errorMessage;
    private List<ErrorDetail> errors;
    SourceErrorEnum errorSource;
    String errorPath;
    Instant errorTimestamp;

    @Data
    @Builder
    public static class ErrorDetail {
        private String field;
        private String message;
    }
}
