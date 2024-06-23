package com.hamdan.concert.model.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseApiError {

    private HttpStatusCode httpStatus;
    private String responseCode;
    private String message;
    private final Long timestamp;

    private ResponseApiError() {
        timestamp = System.nanoTime();
    }

    public ResponseApiError(HttpStatusCode status, String message) {
        this();
        this.httpStatus = status;
        this.message = message;
    }

    public ResponseApiError(HttpStatusCode status, String message, String responseCode) {
        this();
        this.httpStatus = status;
        this.message = message;
        this.responseCode = responseCode;
    }
}
