package com.hamdan.concert.handler.exception;

import com.hamdan.concert.enums.MessageResponse;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class BusinessBadRequestException extends BusinessException {

    @Serial
    private static final long serialVersionUID = -4199881339917644652L;

    public BusinessBadRequestException(@NonNull MessageResponse messageResponse) {
        super(messageResponse, HttpStatus.BAD_REQUEST);
    }

    public BusinessBadRequestException(@NonNull String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public BusinessBadRequestException(@NonNull MessageResponse messageResponse, Object... customMessage) {
        super(messageResponse, HttpStatus.BAD_REQUEST, customMessage);
    }
}
