package com.hamdan.concert.handler.exception;

import com.hamdan.concert.enums.MessageResponse;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class BusinessNoContentException extends BusinessException {

    @Serial
    private static final long serialVersionUID = 2580409049840081039L;

    public BusinessNoContentException(@NonNull MessageResponse messageResponse) {
        super(messageResponse, HttpStatus.NO_CONTENT);
    }

    public BusinessNoContentException(@NonNull String message) {
        super(message, HttpStatus.NO_CONTENT);
    }

    public BusinessNoContentException(@NonNull MessageResponse messageResponse, Object... customMessage) {
        super(messageResponse, HttpStatus.NO_CONTENT, customMessage);
    }
}
