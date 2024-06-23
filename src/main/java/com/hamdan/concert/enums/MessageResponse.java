package com.hamdan.concert.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageResponse {
    OK("ok", "SUCCESS"),
    NO_CONCERT_AVAILABLE("no_concert_available", "No Concert Available"),
    CONCERT_NOT_FOUND("concert_not_found", "Concert data not found"),
    CUSTOMER_NOT_FOUND("customer_not_found", "Customer data not found"),
    CUSTOMER_EXIST("customer_exist", "Customer already exists"),
    CONCERT_EXIST("concert_exist", "Concert already exists");

    public static final String INVALID_TIME_FORMAT = "Invalid time format please use format HH:mm:ss";
    private final String errorCode;
    private final String message;
}
