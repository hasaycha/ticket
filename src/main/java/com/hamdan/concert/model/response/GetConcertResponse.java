package com.hamdan.concert.model.response;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Time;

@Data
public class GetConcertResponse {
    private String code;
    private String name;
    private Integer quota;
    private BigDecimal price;
    private Time timeFrom;
    private Time timeTo;
}
