package br.dev.jstec.tyginvestiment.exception;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static br.dev.jstec.tyginvestiment.exception.BaseErrorMessage.*;
import static br.dev.jstec.tyginvestiment.exception.SourceErrorEnum.APPLICATION;
import static br.dev.jstec.tyginvestiment.exception.SourceErrorEnum.INFRASTRUCTURE;
import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.ResponseEntity.status;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    private static final String PACKAGE_BASE = "br.dev.jstec";

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler({
            ConstraintViolationException.class, MethodArgumentNotValidException.class})
    ResponseEntity<ResponseErrorMessage> handleConstraintViolationException(Exception ex) {
        List<ResponseErrorMessage.ErrorDetail> errors = new ArrayList<>();

        if (ex instanceof MethodArgumentNotValidException) {
            errors = ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors().stream()
                    .map(fieldError -> ResponseErrorMessage.ErrorDetail.builder()
                            .field(fieldError.getField())
                            .message(fieldError.getDefaultMessage())
                            .build())
                    .toList();
        } else if (ex instanceof ConstraintViolationException) {
            var error = ResponseErrorMessage.ErrorDetail.builder()
                    .field(((ConstraintViolationException) ex).getConstraintName())
                    .message(((ConstraintViolationException) ex).getErrorMessage())
                    .build();
            errors.add(error);
        }
        var msg = format(INVALID_INFORMATION_ERROR.getMsg(), ex.getMessage());
        log.error(msg);
        return status(BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        ResponseErrorMessage.builder()
                                .errorCode(INVALID_INFORMATION_ERROR.getCode())
                                .errorMessage(INVALID_INFORMATION_ERROR.getMsg())
                                .errors(errors)
                                .errorSource(INFRASTRUCTURE)
                                .errorPath(refineStackTrace(ex))
                                .errorTimestamp(Instant.now())
                                .build()
                );
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler(MissingServletRequestPartException.class)
    ResponseEntity<ResponseErrorMessage> handleMissingServletRequestPartException(MissingServletRequestPartException ex) {

        var msg = format(format(MISSING_MANDATORY_PARAMETER.getMsg(), "imagem"), ex.getMessage());
        log.error(msg);
        return status(BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ResponseErrorMessage.builder()
                        .errorCode(MISSING_MANDATORY_PARAMETER.getCode())
                        .errorMessage(ex.getMessage())
                        .errorSource(INFRASTRUCTURE)
                        .errorPath(refineStackTrace(ex))
                        .errorTimestamp(Instant.now())
                        .build());
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler(BusinessException.class)
    ResponseEntity<ResponseErrorMessage> handleBusinessException(BusinessException ex) {

        log.error("[BusinessException] - {} - {}", ex.getCode(), ex.getMessage());
        return status(BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ResponseErrorMessage.builder()
                        .errorCode(ex.getCode())
                        .errorMessage(ex.getMessage())
                        .errorSource(APPLICATION)
                        .errorPath(refineStackTrace(ex))
                        .errorTimestamp(Instant.now())
                        .build());
    }


    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler(RequestException.class)
    public ResponseEntity<ResponseErrorMessage> handleRequestException(RequestException ex) {

        log.error(REQUEST_ERROR.getMsg(), ex.getMessage());
        return status(ex.getHttpStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(ResponseErrorMessage.builder()
                        .errorCode(ex.getErrorMessage().getCode())
                        .errorMessage(ex.getErrorMessage().getMessage())
                        .errorSource(INFRASTRUCTURE)
                        .errorPath(refineStackTrace(ex))
                        .errorTimestamp(Instant.now())
                        .build());
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ResponseErrorMessage> handleFeignException(FeignException ex) {

        var msg = format(REQUEST_ERROR.getMsg(), ex.getMessage());
        log.error(msg);
        return status(BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ResponseErrorMessage.builder()
                        .errorCode(REQUEST_ERROR.getCode())
                        .errorMessage(ex.getMessage())
                        .errorSource(INFRASTRUCTURE)
                        .errorPath(refineStackTrace(ex))
                        .errorTimestamp(Instant.now())
                        .build());
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler(InfrastructureException.class)
    public ResponseEntity<ResponseErrorMessage> handleInfrastructureException(InfrastructureException ex) {

        var msg = format(REQUEST_ERROR.getMsg(), ex.getMessage());
        log.error(msg);
        return status(BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ResponseErrorMessage.builder()
                        .errorCode(ex.getErrorMessage().getCode())
                        .errorMessage(ex.getMessage())
                        .errorSource(INFRASTRUCTURE)
                        .errorPath(refineStackTrace(ex))
                        .errorTimestamp(Instant.now())
                        .build());
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseErrorMessage> handleRunException(RuntimeException ex) {

        log.error(format(ERRO_NAO_IMPLEMENTADO.getMsg(), ex.getMessage()));
        return status(BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ResponseErrorMessage.builder()
                        .errorCode(ERRO_NAO_IMPLEMENTADO.getCode())
                        .errorMessage(ex.getMessage())
                        .errorSource(INFRASTRUCTURE)
                        .errorPath(refineStackTrace(ex))
                        .errorTimestamp(Instant.now())
                        .build());
    }

    private String refineStackTrace(Exception ex) {
        var stackTrace = ex.getStackTrace();
        var path = EMPTY;
        for (StackTraceElement element : stackTrace) {
            if (element.getClassName().startsWith(PACKAGE_BASE)) {
                String fileName = element.getFileName();
                String className = element.getClassName();
                String methodName = element.getMethodName();
                int lineNumber = element.getLineNumber();
                path = """
                        Class:\s""" + className + """
                        Method:\s""" + methodName + """
                        File:\s""" + fileName + """
                        Line:\s""" + lineNumber;
                break;
            }
        }
        return path;
    }
}
