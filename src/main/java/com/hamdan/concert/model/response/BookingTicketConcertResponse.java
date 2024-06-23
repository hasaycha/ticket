package com.hamdan.concert.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingTicketConcertResponse {
    private String transactionCode;
    private String concertCode;
    private String concertName;
    private String customerName;
    private String customerEmail;
    private Integer qty;
    private BigDecimal totalPrice;
}
