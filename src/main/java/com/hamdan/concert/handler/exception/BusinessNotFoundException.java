package com.hamdan.concert.handler.exception;

import com.hamdan.concert.enums.MessageResponse;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class BusinessNotFoundException extends BusinessException {

    @Serial
    private static final long serialVersionUID = 7397987606336110377L;

    public BusinessNotFoundException(@NonNull MessageResponse messageResponse) {
        super(messageResponse, HttpStatus.NOT_FOUND);
    }

    public BusinessNotFoundException(@NonNull String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public BusinessNotFoundException(@NonNull MessageResponse messageResponse, Object... customMessage) {
        super(messageResponse, HttpStatus.NOT_FOUND, customMessage);
    }
}
