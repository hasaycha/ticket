package com.hamdan.concert.handler.exception;

import com.hamdan.concert.enums.MessageResponse;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.io.Serial;

@Getter
public class BusinessException extends HttpClientErrorException {

    @Serial
    private static final long serialVersionUID = 7429935230922781029L;
    
    private final String responseCode;
    private final String message;
    private MessageResponse messageResponse;
    private Object[] customMessage;

    public BusinessException(@NonNull MessageResponse messageResponse, @NonNull HttpStatus httpStatus) {
        super(httpStatus);
        this.responseCode = messageResponse.getErrorCode();
        this.message = messageResponse.getMessage();
        this.messageResponse = messageResponse;
    }

    public BusinessException(@NonNull MessageResponse messageResponse) {
        super(HttpStatus.BAD_REQUEST);
        this.responseCode = messageResponse.getErrorCode();
        this.message = messageResponse.getMessage();
        this.messageResponse = messageResponse;
    }

    public BusinessException(String message, @NonNull HttpStatus httpStatus) {
        super(httpStatus);
        this.responseCode = String.valueOf(httpStatus.value());
        this.message = message;
    }

    public BusinessException(@NonNull MessageResponse messageResponse, @NonNull HttpStatus httpStatus, Object... customMessage) {
        super(httpStatus);
        this.responseCode = messageResponse.getErrorCode();
        this.message = String.format(messageResponse.getMessage(), customMessage);
        this.customMessage = customMessage;
        this.messageResponse = messageResponse;
    }
}
