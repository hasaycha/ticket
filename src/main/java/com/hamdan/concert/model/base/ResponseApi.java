package com.hamdan.concert.model.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hamdan.concert.enums.MessageResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseApi<T> {

    private final Long timestamp;
    private String responseCode = MessageResponse.OK.getErrorCode();
    private String message = MessageResponse.OK.getMessage();
    private T data;
    private HttpStatus httpStatus;

    public ResponseApi() {
        timestamp = System.nanoTime();
    }

    public ResponseApi(T data) {
        this();
        this.data = data;
    }

    public ResponseApi(HttpStatus status, MessageResponse messageResponse, T data) {
        this();
        this.httpStatus = status;
        this.responseCode = messageResponse.getErrorCode();
        this.message = messageResponse.getMessage();
        this.data = data;
    }
}
