package com.hamdan.concert.model.request;

import com.hamdan.concert.validator.TimeFormatConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateConcertRequest {
    @NotBlank
    private String name;

    @NotNull
    @Min(1)
    private Integer quota;

    @NotNull
    @Min(1)
    private BigDecimal price;

    @TimeFormatConstraint
    private String timeFrom;

    @TimeFormatConstraint
    private String timeTo;
}
