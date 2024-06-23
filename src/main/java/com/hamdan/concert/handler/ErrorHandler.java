package com.hamdan.concert.handler;

import com.hamdan.concert.handler.exception.BusinessException;
import com.hamdan.concert.model.base.ResponseApiError;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;

import javax.xml.bind.ValidationException;
import java.util.Objects;
import java.util.Optional;

@ControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler({RuntimeException.class, HttpRequestMethodNotSupportedException.class, HttpClientErrorException.class,
            BindException.class, NullPointerException.class})
    public ResponseEntity<ResponseApiError> onRuntimeException(Exception ex) {
        HttpStatusCode httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String errorMessage = ex.getMessage();
        if (ex instanceof HttpClientErrorException httpClientErrorException) {
            httpStatus = httpClientErrorException.getStatusCode();
        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
            httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
        } else if (ex instanceof MethodArgumentConversionNotSupportedException methodArgumentConversionNotSupportedException) {
            errorMessage = methodArgumentConversionNotSupportedException.getCause().getMessage();
        } else if (ex instanceof BindException bindException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            errorMessage = getErrorMessageFromBindException(bindException);
        } else if (ex instanceof NullPointerException nullPointerException) {
            errorMessage = nullPointerException.getClass().getName() + " " + errorMessage;
        } else if (ex instanceof ConstraintViolationException) {
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof ValidationException validationException && (Objects.nonNull(validationException.getCause()) && validationException.getCause() instanceof BusinessException)) {
            errorMessage = validationException.getCause().getMessage();
            httpStatus = ((BusinessException) ex.getCause()).getStatusCode();
        }

        ResponseApiError responseApiError = new ResponseApiError(httpStatus, errorMessage != null ? errorMessage : "Cause cannot get NULL");
        return buildResponseEntity(responseApiError, ex);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseApiError> handleBusinessException(BusinessException ex) {
        ResponseApiError apiError = new ResponseApiError(ex.getStatusCode(), ex.getMessage(), ex.getResponseCode());
        return buildResponseEntity(apiError, ex);
    }

    private ResponseEntity<ResponseApiError> buildResponseEntity(ResponseApiError apiError, Exception throwable) {
        if (apiError.getHttpStatus().is4xxClientError()) {
            log.warn("{}-{}", Optional.ofNullable(apiError.getResponseCode()).orElse(String.valueOf(apiError.getHttpStatus().value())), apiError.getMessage());
        } else if (apiError.getHttpStatus().is5xxServerError()) {
            log.error("{}-{}", Optional.ofNullable(apiError.getResponseCode()).orElse(String.valueOf(apiError.getHttpStatus().value())), apiError.getMessage(), throwable);
        } else if (apiError.getHttpStatus().is2xxSuccessful()) {
            log.info("{}-{}", Optional.ofNullable(apiError.getResponseCode()).orElse(String.valueOf(apiError.getHttpStatus().value())), apiError.getMessage());
        }
        return ResponseEntity
                .status(apiError.getHttpStatus())
                .body(apiError);
    }

    private String getErrorMessageFromBindException(BindException bindException) {
        StringBuilder errors = new StringBuilder();
        for (int i = 0; i < bindException.getFieldErrors().size(); i++) {
            if (i != bindException.getFieldErrors().size() - 1) {
                errors.append(bindException.getFieldErrors().get(i).getField()).append(" ").append(bindException.getFieldErrors().get(i).getDefaultMessage()).append(" and ");
            } else {
                errors.append(bindException.getFieldErrors().get(i).getField()).append(" ").append(bindException.getFieldErrors().get(i).getDefaultMessage());
            }
        }

        return errors.toString();
    }

}
