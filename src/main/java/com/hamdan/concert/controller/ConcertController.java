package com.hamdan.concert.controller;

import com.hamdan.concert.model.base.ResponseApi;
import com.hamdan.concert.model.request.BookingConcertRequest;
import com.hamdan.concert.model.request.CreateConcertRequest;
import com.hamdan.concert.model.response.BookingTicketConcertResponse;
import com.hamdan.concert.model.response.GetConcertResponse;
import com.hamdan.concert.service.ConcertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/concerts")
@RequiredArgsConstructor
@Validated
public class ConcertController {
    private final ConcertService concertService;

    @Operation(summary = "Create concert")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success Create concert"),
            @ApiResponse(responseCode = "400", description = "Invalid data request", content = @Content),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseApi<GetConcertResponse> createConcert(@Valid @RequestBody CreateConcertRequest createConcertRequest) {
        return concertService.createConcert(createConcertRequest);
    }

    @Operation(summary = "Get All Available Concert")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success get available concert"),
            @ApiResponse(responseCode = "400", description = "Invalid data request", content = @Content),
            @ApiResponse(responseCode = "204", description = "No content", content = @Content),
    })
    @GetMapping("/available")
    @ResponseStatus(HttpStatus.OK)
    public ResponseApi<List<GetConcertResponse>> getListAvailableConcert() {
        return concertService.getListAvailableConcert();
    }

    @Operation(summary = "Get Available Concert By Code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success get available concert"),
            @ApiResponse(responseCode = "400", description = "Invalid data request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Concert not found", content = @Content),
    })
    @GetMapping("/{code}/available")
    @ResponseStatus(HttpStatus.OK)
    public ResponseApi<GetConcertResponse> getByCodeAndAvailabilityConcert(@PathVariable(name = "code") String code) {
        return concertService.getByCodeAndAvailabilityConcert(code);
    }

    @Operation(summary = "Book concert ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success book concert ticket"),
            @ApiResponse(responseCode = "400", description = "Invalid data request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Concert not found", content = @Content),
    })
    @PostMapping("/{code}/book")
    @ResponseStatus(HttpStatus.OK)
    public ResponseApi<BookingTicketConcertResponse> bookingTicketConcert(@PathVariable(name = "code") String code,
                                                                          @Valid @RequestBody BookingConcertRequest bookingConcertRequest) {
        return concertService.bookingConcert(code, bookingConcertRequest);
    }
}
