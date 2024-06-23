package com.hamdan.concert.service;

import com.hamdan.concert.model.base.ResponseApi;
import com.hamdan.concert.model.request.BookingConcertRequest;
import com.hamdan.concert.model.response.BookingTicketConcertResponse;
import com.hamdan.concert.model.response.GetConcertResponse;

import java.util.List;

public interface ConcertService {
    ResponseApi<List<GetConcertResponse>> getListAvailableConcert();

    ResponseApi<GetConcertResponse> getByCodeAndAvailabilityConcert(String code);

    ResponseApi<BookingTicketConcertResponse> bookingConcert(String code, BookingConcertRequest bookingConcertRequest);
}
