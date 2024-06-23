package com.hamdan.concert.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingConcertRequest {
    @NotNull
    @Min(1)
    private Integer qty;

    @NotBlank
    private String email;
}
